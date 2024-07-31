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
 * @since 2024-07-29
 */
@Getter
@Setter
@TableName("cc_fans")
@ApiModel(value = "FansEntity对象", description = "")
public class Fans implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("被关注的人的uid")
    private Integer userId;

    @ApiModelProperty("关注的人的uid")
    private Integer followUserId;

    @ApiModelProperty("关注的时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    @ApiModelProperty("是否关注")
    private Integer isFollow;

    @Override
    public String toString() {
        return "Fans{" +
                "id=" + id +
                ", userId=" + userId +
                ", followUserId=" + followUserId +
                ", createTime=" + createTime +
                ", isFollow=" + isFollow +
                '}';
    }
}
