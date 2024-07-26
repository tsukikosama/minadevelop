package com.miku.minadevelop.modules.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author miku
 * @since 2024-07-19
 */
@Getter
@Setter
@TableName("cc_order")
@ApiModel(value = "Order对象", description = "order")
@Api("订单对象")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Integer orderId;

    @ApiModelProperty("订单创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("订单支付时间")
    @TableField("pay_time")
    private LocalDateTime payTime;

    @ApiModelProperty("订单的总价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("商品编号")
    @TableField("product_id")
    private Integer productId;

    @ApiModelProperty("支付人id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("订单的状态1待支付 2支付中 3支付完成 4退款")
    @TableField("state")
    private Boolean state;

    @ApiModelProperty("逻辑删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
