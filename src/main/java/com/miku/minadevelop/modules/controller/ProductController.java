package com.miku.minadevelop.modules.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.entity.Product;
import com.miku.minadevelop.modules.service.IProductService;
import com.miku.minadevelop.modules.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-06-19
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductServiceImpl service;

    @Value("${minadevelop.fileurl}")
    public String fileUrl;

    @PostMapping("/save")
    public Result saveProduct(@RequestBody Product product){
        System.out.println(product.getFileUrl());
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
               String encryptedZipFilePath = unzipFile+"/" + split[1];
               ZipFile encryptedZipFile = new ZipFile(encryptedZipFilePath, password.toCharArray());
               // 创建 ZipParameters 对象，并设置压缩级别和加密方法
               ZipParameters zipParameters = new ZipParameters();
               zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
               zipParameters.setEncryptFiles(true);
               zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
               // 添加文件到压缩包
               encryptedZipFile.addFile(file, zipParameters);
               product.setZipPsd(password);
               System.out.println("文件已成功压缩并加密！");
               service.save(product);
               //把原来的文件删除
               file.delete();
           }catch (Exception e){
                log.error("文件加密失败",e);
                throw new RuntimeException("文件解压失败");
           }

         }

        return Result.ok("提交成功");
    }

    /**
     *通过productid 查询对应的文件路径
     */
    public Result getProductById(String id){
        Product product = service.getById(id);
        return Result.ok(product);
    }
}
