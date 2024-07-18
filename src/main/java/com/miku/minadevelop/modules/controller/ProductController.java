package com.miku.minadevelop.modules.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.common.page.CommonQuery;
import com.miku.minadevelop.modules.entity.Product;
import com.miku.minadevelop.modules.entity.Tag;
import com.miku.minadevelop.modules.entity.User;
import com.miku.minadevelop.modules.pojo.ProductPoJo;
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
import java.util.List;
import java.util.stream.Collectors;


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

    private final IProductService productService;

    @PostMapping("/save")
    public Result saveProduct(@RequestBody Product product){
        productService.savezip(product);
        return Result.ok("提交成功");
    }

    /**
     *通过productid 查询对应的文件路径
     */
    public Result getProductById(String id){
        Product product = productService.getById(id);
        return Result.ok(product);
    }

    /**
     * 获取分页全部的product
     * @param query
     * @return
     */
    @GetMapping("/list")
    public Result getList(CommonQuery query){
        System.out.println(query);
//        List<Product> list = service.list();
//        List<ProductPoJo> collect = list.stream().map(item -> {
//            return BeanUtil.copyProperties(item, ProductPoJo.class);
//        }).collect(Collectors.toList());
        Page<Product> page = productService.page(new Page<>(query.getCurrent(), query.getPageSize()));
        return Result.ok(page);
    }
    /**
     * 用户获取解压密码
     */
    @GetMapping("/getpsd/{pid}")
    public Result getPsdByProId(@PathVariable("pid")String pid){
        //通过登录的token获取用户id
        Object loginId = StpUtil.getTokenInfo().getLoginId();
        Product pro = productService.getOne(Wrappers.<Product>lambdaQuery().eq(Product::getProductId, pid).eq(Product::getUid, loginId));
        return Result.ok(pro.getZipPsd());
    }

    /**
     * 通过tagid获取对应的商品
     * @param tid
     * @return
     */
    @GetMapping("/list/{tid}")
    public Result getProductByTagId(@PathVariable("tid")String tid){
        List<ProductPoJo> list = productService.getListByTagId(tid);
        return Result.ok(list);
    }

    /**
     * 查询商品详情
     * @param pid
     * @return
     */
    @GetMapping("/{pid}")
    public Result getProductDetail(@PathVariable("pid") Integer pid){
        return Result.ok(productService.getById(pid));
    }




}
