package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Product;
import com.miku.minadevelop.modules.mapper.ProductMapper;
import com.miku.minadevelop.modules.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-06-19
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
