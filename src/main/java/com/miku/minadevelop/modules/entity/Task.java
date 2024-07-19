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
 * @since 2024-07-19
 */
@Getter
@Setter
@TableName("cc_task")
@ApiModel(value = "Task对象", description = "")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("任务id")
    @TableField("task_id")
    private Integer taskId;

    @ApiModelProperty("任务标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("任务描述")
    @TableField("content")
    private String content;

    @ApiModelProperty("附件地址")
    @TableField("file")
    private String file;

    @ApiModelProperty("发布时间")
    @TableField("create_datetime")
    private LocalDateTime createDatetime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("任务状态 0正常 1 结束")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("逻辑删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty("发布人id")
    @TableField("user_id")
    private Integer userId;
}
