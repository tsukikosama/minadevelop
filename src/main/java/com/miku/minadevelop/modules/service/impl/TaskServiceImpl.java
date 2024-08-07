package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Task;
import com.miku.minadevelop.modules.mapper.TaskMapper;
import com.miku.minadevelop.modules.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
