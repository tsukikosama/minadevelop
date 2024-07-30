package com.miku.minadevelop.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miku.minadevelop.modules.entity.Fans;
import com.baomidou.mybatisplus.extension.service.IService;
import com.miku.minadevelop.modules.request.FollowReq;
import com.miku.minadevelop.modules.response.FansResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author miku
 * @since 2024-07-29
 */
public interface IFansService extends IService<Fans> {

    IPage<FansResp> selectPage(IPage<Object> page, Long uid);

    void follow(FollowReq req);
    public void executeAddFans();
}
