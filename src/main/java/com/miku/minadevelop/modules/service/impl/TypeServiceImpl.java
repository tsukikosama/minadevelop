package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Type;
import com.miku.minadevelop.modules.mapper.TypeMapper;
import com.miku.minadevelop.modules.service.ITypeService;
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
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

}
