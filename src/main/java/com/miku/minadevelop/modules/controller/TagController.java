package com.miku.minadevelop.modules.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.entity.Tag;
import com.miku.minadevelop.modules.service.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagServiceImpl tagService;

    /**
     * 获取全部的list
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        return Result.ok(tagService.list());
    }

    /**
     * 通过typeid获取对应的tag
     */
    @GetMapping("/list/{tid}")
    public Result getTagById(@PathVariable("tid")Integer tid){
        List<Tag> list = tagService.list(Wrappers.<Tag>lambdaQuery().eq(Tag::getTypeId, tid));
        return Result.ok(list);
    }



}
