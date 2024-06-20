package com.miku.minadevelop.modules.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2024-06-19
 */
@Getter
@Setter
@TableName("cc_product")
@ApiModel(value = "Product对象", description = "")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    @ApiModelProperty("商品名")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("发布时间")
    @TableField("product_time")
    private String productTime;

    @ApiModelProperty("用户id")
    @TableField("uid")
    private Integer uid;

    @ApiModelProperty("购买次数")
    @TableField("count")
    private Integer count;

    @ApiModelProperty("是否有压缩密码")
    @TableField("has_psd")
    private Integer hasPsd;

    @ApiModelProperty("压缩包密码")
    @TableField("zip_psd")
    private String zipPsd;

    @ApiModelProperty("文件地址")
    @TableField("file_url")
    private String fileUrl;
}
