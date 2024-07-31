package com.miku.minadevelop.modules.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.miku.minadevelop.modules.entity.Fans;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miku.minadevelop.modules.response.FansResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author miku
 * @since 2024-07-29
 */
@Mapper
public interface FansMapper extends BaseMapper<Fans> {

    IPage<FansResp> selectCustomPage(IPage<Object> page, @Param(Constants.WRAPPER)QueryWrapper<Fans> userId);


    FansResp selectFansByUserId(@Param("uid")Long userId,@Param("followUid")String followUid);
}
