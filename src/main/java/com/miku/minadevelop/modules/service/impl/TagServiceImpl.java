package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Tag;
import com.miku.minadevelop.modules.mapper.TagMapper;
import com.miku.minadevelop.modules.service.ITagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
