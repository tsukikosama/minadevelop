package com.miku.minadevelop.modules.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.modules.entity.Order;
import com.miku.minadevelop.modules.mapper.OrderMapper;
import com.miku.minadevelop.modules.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miku.minadevelop.modules.status.OrderStatusMenu;
import com.miku.minadevelop.modules.utils.IdUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miku
 * @since 2024-06-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    /**
     *
     * @param uid
     * @return
     */
    @Override
    public List<Order> listById(Integer uid) {
        List<Order> list = this.list(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, uid));
        return list;
    }

    @Override
    public void saveOrder(Order order) {
        order.setOrderId(IdUtils.randomId(6));
        order.setCreateTime(DateUtil.now());
        order.setState(OrderStatusMenu.UN_PAY.getValue());

        boolean flag = this.save(order);
        if (!flag){
            throw new CustomException("保存失败请稍后再试");
        }

    }


}
