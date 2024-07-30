package com.miku.minadevelop.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.common.page.CommonQuery;
import com.miku.minadevelop.modules.entity.Fans;
import com.miku.minadevelop.modules.request.FollowReq;
import com.miku.minadevelop.modules.response.FansResp;
import com.miku.minadevelop.modules.service.IFansService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-07-29
 */
@RestController
@RequestMapping("/fans")
@RequiredArgsConstructor
@Slf4j
public class FansController {

    private final IFansService fansService;

    @GetMapping("/fansList/{uid}")
    public Result list(CommonQuery query , @PathVariable("uid") Long uid){
        IPage<FansResp> page = fansService.selectPage(query.getPage(),uid);
        return Result.ok(page);
    }

    @PostMapping("/follow")
    public Result follow(@RequestBody @Validated FollowReq req){
        fansService.follow(req);
        return Result.ok();
    }

    @GetMapping("/test")
    public String test(){
        fansService.executeAddFans();
        return "test";
    }
}
