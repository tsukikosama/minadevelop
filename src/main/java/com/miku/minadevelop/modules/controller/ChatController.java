package com.miku.minadevelop.modules.controller;

import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.request.ChatRelationReq;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import com.miku.minadevelop.modules.response.MessageResp;
import com.miku.minadevelop.modules.service.IChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Api("聊天关系接口")
public class ChatController {

    private final IChatService chatService;

    /**
     * 通过用户查询到聊天的信息
     * @param uid
     * @return
     */
    @ApiOperation("查询用户聊天全部关系的接口")
    @GetMapping("/relation/{uid}")
    public Result getUserChatRelation(@PathVariable("uid")String uid){
//        chatService.list()
        List<MessageResp> list = chatService.listUserChat(uid);
        return Result.ok(list);
    }

    @ApiOperation("生成用户聊天关系的接口")
    @PostMapping("/relation")
    public Result createRelation(@RequestBody @Validated ChatRelationReq req){
        Long relation = chatService.createRelation(req);
        return Result.ok(relation);
    }




}
