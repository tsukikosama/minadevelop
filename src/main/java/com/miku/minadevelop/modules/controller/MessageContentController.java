package com.miku.minadevelop.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.entity.MessageContent;
import com.miku.minadevelop.modules.request.MessageReq;
import com.miku.minadevelop.modules.service.IMessageContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-07-18
 */
@RestController
@RequestMapping("/messageContent")
@RequiredArgsConstructor
public class MessageContentController {


    private final IMessageContentService messageContentService;

    @PostMapping("/send")
    public Result sendMsg(@RequestBody @Validated MessageReq messageReq){
        System.out.println(messageReq);
        MessageContent messageContent = BeanUtil.copyProperties(messageReq, MessageContent.class);

        return Result.ok();
    }



}
