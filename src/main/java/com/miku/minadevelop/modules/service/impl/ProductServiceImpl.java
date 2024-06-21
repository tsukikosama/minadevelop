package com.miku.minadevelop.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.modules.entity.Product;
import com.miku.minadevelop.modules.mapper.ProductMapper;
import com.miku.minadevelop.modules.pojo.ProductPoJo;
import com.miku.minadevelop.modules.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-06-19
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Value("${minadevelop.fileurl}")
    public String fileUrl;

    @Value("${minadevelop.weburl}")
    private String webUrl;
    @Override
    public void savezip(Product product) {
        product.setProductId(6);
        product.setUid(6);
        product.setProductTime(DateUtil.now());
        product.setProductName("test");
//        product.setCount(0);
        product.setHasPsd(1);
        File file ;
        //判断是否需要加密
        if (product.getHasPsd() > 0){
            //需要加密的名字
            String fileUrl1 = product.getFileUrl();
            String[] split = fileUrl1.split("/upload/");
            //读取本地的文件
            String localFile = fileUrl + split[1];
            String unzipFile = fileUrl + "unzip";
            File dir = new File(unzipFile);
            dir.mkdir();
            //获取目标的文件
            file =  new File(localFile);
            //判断文件是否存在
            try {
                if (!file.exists()){
                    log.info("文件不存在{}",product.getFileUrl());
                    throw new FileNotFoundException("文件不存在");
                }
                //生成随机的压缩密码
                String password = RandomUtil.randomString(10);
                //把zip保存到本地
//               File file1 = zipFile.getFile();
                // 创建 ZipFile 对象，并设置输出路径和密码
                String encryptedZipFilePath = fileUrl +"product"+ split[1] ;
                ZipFile encryptedZipFile = new ZipFile(encryptedZipFilePath, password.toCharArray());
                // 创建 ZipParameters 对象，并设置压缩级别和加密方法
                ZipParameters zipParameters = new ZipParameters();
                zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
                // 添加文件到压缩包
                encryptedZipFile.addFile(file, zipParameters);
                product.setZipPsd(password);
                product.setFileUrl(webUrl+"product"+ split[1]);
                System.out.println("文件已成功压缩并加密！");
                save(product);
                //把原来的文件删除
                file.delete();
            }catch (Exception e){
                log.error("文件加密失败",e);
                throw new RuntimeException("文件解压失败");
            }

        }
    }

    @Override
    public List<ProductPoJo> getListByTagId(String tid) {
        /**
         *  通过tid查找到全部的product
         */
        List<Product> list = list(Wrappers.<Product>lambdaQuery().apply("FIND_IN_SET({0}, tag_id)", tid));

        List<ProductPoJo> collect = list.stream().map(item -> {
            return BeanUtil.copyProperties(item, ProductPoJo.class);
        }).collect(Collectors.toList());
        return collect;
    }
}
