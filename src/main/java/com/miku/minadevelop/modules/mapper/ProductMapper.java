package com.miku.minadevelop.modules.mapper;

import com.miku.minadevelop.modules.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
