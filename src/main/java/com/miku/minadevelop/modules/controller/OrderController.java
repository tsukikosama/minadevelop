package com.miku.minadevelop.modules.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miku.minadevelop.common.Result;
import com.miku.minadevelop.common.exception.CustomException;
import com.miku.minadevelop.common.page.CommonQuery;
import com.miku.minadevelop.modules.entity.Order;
import com.miku.minadevelop.modules.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miku
 * @since 2024-06-27
 */
@RestController
@RequestMapping("/modules/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderServiceImpl orderService;

    /**
     * 查询用户对应的订单
     * @param uid
     * @return
     */
    @GetMapping("/list/{uid}")
    public Result getOrderByUid(CommonQuery query ,@PathVariable("uid") Integer uid){
       List<Order> list =  orderService.listById(uid);
       return Result.ok(list);
    }

    /**
     * 查询全部的订单列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        List<Order> list = orderService.list();
        return Result.ok(list);
    }

    /**
     *  通过订单id查询订单
     */
    @GetMapping("/{oid}")
    public Result getOrder(@RequestParam("oid")Integer oid){
        Order order = orderService.getById(oid);
        return Result.ok(order);
    }

    /**
     * 通过订单id删除订单
     */
    @PostMapping("/{oid}")
    public Result delete(@PathVariable("oid")Integer oid){
        boolean b = orderService.removeById(oid);
        if (!b){
            log.error("删除订单{}失败",oid);
            throw new CustomException("删除失败");
        }
        return Result.ok("删除成功");
    }

    /**
     * 保存订单
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody Order order){
        orderService.saveOrder(order);
        return Result.ok("保存成功");
    }

    /**
     * 支付订单
     */
    @PostMapping("/pay")
    public Result pay(@RequestParam("oid")Integer oid){
        return Result.ok();
    }

    @PostMapping("/refund")
    public Result refund(@RequestParam("oid")Integer oid){
        return Result.ok();
    }

}
