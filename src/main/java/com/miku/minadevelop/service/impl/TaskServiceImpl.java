package com.miku.minadevelop.service.impl;

import com.miku.minadevelop.entity.Task;
import com.miku.minadevelop.mapper.TaskMapper;
import com.miku.minadevelop.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-06-13
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
