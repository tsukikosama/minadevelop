package com.miku.minadevelop.modules.entity;

import com.baomidou.mybatisplus.annotation.*;

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
 * @since 2024-07-19
 */
@Getter
@Setter
@TableName("cc_product")
@ApiModel(value = "Product对象", description = "")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("主键")
    @TableField("product_id")
    private Integer productId;

    @ApiModelProperty("商品名字")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("下载次数")
    @TableField("count")
    private Integer count;

    @ApiModelProperty("解压密码")
    @TableField("zip_psd")
    private String zipPsd;

    @ApiModelProperty("是否需要解压密码 0为不需要 1为需要")
    @TableField("has_psd")
    private Boolean hasPsd;

    @ApiModelProperty("文件路径")
    @TableField("file_url")
    private String fileUrl;
}
