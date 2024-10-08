package com.miku.minadevelop.modules.controller;

import cn.hutool.core.bean.BeanUtil;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.modules.entity.Type;
import com.miku.minadevelop.modules.service.ITypeService;
import com.miku.minadevelop.modules.service.impl.TypeServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
@Slf4j
@Api("帖子分类接口")
public class TypeController {
    private final ITypeService typeService;
    /**
     * 获取type列表
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        return Result.ok(typeService.list());
    }

    /**
     * 通过tpyeid来获取type
     */
    @GetMapping("/{tid}")
    public Result getTypeById(@PathVariable("tid") Integer tid) {
        return Result.ok(typeService.getOptById(tid));
    }

    /**
     * 保存type
     * @param type
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Type type){
        if (BeanUtil.isEmpty((type))){
            return Result.fail("添加失败",406);
        }
        return Result.ok(typeService.save(type));
    }

    /**
     * 更新type
     * @param type
     * @return
     */
    @PostMapping("/update")
    public Result Update(@RequestBody Type type){
        if (BeanUtil.isEmpty(type)){
            log.info("需要保存的信息为:{}",type);
            throw new CustomException("参数异常");
        }
        typeService.save(type);
        return Result.ok("保存成功");
    }

    @GetMapping("/test")
    public Result test(){
        try {

        }catch (Exception e){
            throw new CustomException("报错拉");
        }
        throw new CustomException("报错拉");
//        return Result.fail("500服务器错误",500);
    }
}
