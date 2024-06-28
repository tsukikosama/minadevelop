package com.miku.minadevelop.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author miku
 * @since 2024-06-27
 */
@Getter
@Setter
@TableName("cc_order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    @ApiModelProperty("订单创建时间")
    @TableField("create_time")
    private String createTime;

    @ApiModelProperty("订单支付时间")
    @TableField("pay_time")
    private String payTime;

    @ApiModelProperty("订单的总价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("商品编号")
    @TableField("product_id")
    private String productId;

    @ApiModelProperty("支付人id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("订单的状态1待支付 2支付中 3支付完成 4退款")
    @TableField("state")
    private Integer state;

    @ApiModelProperty("逻辑删除")
    @TableField("is_delete")
    private Integer isDelete;
}
