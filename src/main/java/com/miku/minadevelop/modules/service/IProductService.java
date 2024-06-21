package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miku.minadevelop.modules.pojo.ProductPoJo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author miku
 * @since 2024-06-19
 */
public interface IProductService extends IService<Product> {

    void savezip(Product product);

    List<ProductPoJo> getListByTagId(String tid);
}
