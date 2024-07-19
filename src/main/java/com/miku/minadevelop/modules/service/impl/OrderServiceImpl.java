package com.miku.minadevelop.modules.service.impl;

import com.miku.minadevelop.modules.entity.Order;
import com.miku.minadevelop.modules.mapper.OrderMapper;
import com.miku.minadevelop.modules.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
