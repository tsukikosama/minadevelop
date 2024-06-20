package com.miku.minadevelop.modules.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPoJo {

    @ApiModelProperty("主键")
    private Integer productId;

    @ApiModelProperty("商品名")
    private String productName;

    @ApiModelProperty("发布时间")
    private String productTime;

    @ApiModelProperty("用户id")
    private Integer uid;

    @ApiModelProperty("购买次数")
    private Integer count;

    @ApiModelProperty("是否有压缩密码")
    private Integer hasPsd;


    @ApiModelProperty("文件地址")
    private String fileUrl;

    @ApiModelProperty("商品价格")
    private String productPrice;
}
