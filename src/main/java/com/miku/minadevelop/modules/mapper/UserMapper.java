package com.miku.minadevelop.modules.mapper;

import com.miku.minadevelop.modules.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
