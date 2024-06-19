package com.miku.minadevelop.modules.entity;

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
 * @since 2024-06-13
 */
@Getter
@Setter
@TableName("cc_type")
@ApiModel(value = "Type对象", description = "")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId("type_id")
    private Integer typeId;

    @ApiModelProperty("类型名")
    @TableField("type_name")
    private String typeName;

    @ApiModelProperty("更新时间")
    @TableField("create_time")
    private String createTime;


}
