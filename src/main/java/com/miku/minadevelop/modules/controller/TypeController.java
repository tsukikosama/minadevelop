package com.miku.minadevelop.modules.controller;


import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.service.impl.TypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeServiceImpl typeService;


    /**
     * 获取type列表
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        return Result.ok(typeService.list());
    }


}
