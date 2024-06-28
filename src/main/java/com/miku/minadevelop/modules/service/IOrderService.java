package com.miku.minadevelop.modules.service;

import com.miku.minadevelop.modules.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author miku
 * @since 2024-06-27
 */
public interface IOrderService extends IService<Order> {

    List<Order> listById(Integer uid);

    void saveOrder(Order order);
}
