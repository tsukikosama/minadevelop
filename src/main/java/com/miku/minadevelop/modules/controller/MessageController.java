package com.miku.minadevelop.modules.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.entity.Message;
import com.miku.minadevelop.modules.enums.MessageStatusEnum;
import com.miku.minadevelop.modules.response.MessageEntityResp;
import com.miku.minadevelop.modules.service.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.miku.minadevelop.modules.enums.MessageStatusEnum.UN_READ;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
@Api("消息接口")
public class MessageController {
    private final IMessageService messageService;

    /**
     * 查询用户未读消息的数量
     * @param uid
     * @return
     */
    @ApiOperation("查询用户未读消息的数量")
    @GetMapping("/list/{uid}")
    public Result getUnReadMessageCount(@PathVariable("uid")Integer uid){
        log.info("需要查询的uid{}",uid);
        List<Message> list = messageService.list(Wrappers.<Message>lambdaQuery().eq(Message::getReceiverUid, uid)
                .eq(Message::getStatus, UN_READ.getValue()  ));
        return Result.ok(list.size());
    }

    /**
     *  查询未读的消息
     */
    @ApiOperation("查询未读的消息")
    @GetMapping("/detail/{uid}}")
    public Result getMessage(@PathVariable("uid")Integer uid){
        Map<Long,List<MessageEntityResp>> list = messageService.listUnreadMsg(uid);
        return Result.ok(list);
    }

    @ApiOperation("查询两个用户的消息")
    @GetMapping("/details/{chatId}")
    public Result getDetailListByChatId(@PathVariable("chatId")Integer uid){
        List<MessageEntityResp> list = messageService.listDetail(uid);
        return Result.ok(list);
    }

    @ApiOperation("获取更新消息的通知")
    @GetMapping("/notifi/{chatId}")
    public Result notifi(@PathVariable("chatId")Long chatId){
        messageService.update(Wrappers.<Message>lambdaUpdate().eq(Message::getChatId,chatId).set(Message::getStatus, MessageStatusEnum.READ.getValue()));
        return Result.ok();
    }

}
