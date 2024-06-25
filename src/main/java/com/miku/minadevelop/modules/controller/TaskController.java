package com.miku.minadevelop.modules.controller;


import cn.hutool.core.bean.BeanUtil;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.modules.entity.Task;
import com.miku.minadevelop.modules.service.impl.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;

    /**
     * 获取全部的任务
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        return Result.ok(taskService.list());
    }

    /**
     * 通过taskid来寻找任务
     * @param tid
     * @return
     */
    @GetMapping("/{tid}")
    public Result FindTaskByTaskId(@PathVariable("/tid") Integer tid){
        return Result.ok(taskService.getById(tid));
    }

    /**
     * 保存任务
     * @param task
     * @return
     */
    @PostMapping("/save")
    public  Result Save(@RequestBody Task task){
        if (BeanUtil.isEmpty(task)){
            return Result.fail("任务报错失败请稍后再试",405);
        }
        return Result.ok(taskService.save(task));
    }

    

}