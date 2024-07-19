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
@TableName("cc_message")
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("消息id")
    @TableField("message_id")
    private Integer messageId;

    @ApiModelProperty("发送者的id")
    @TableField("send_id")
    private Integer sendId;

    @ApiModelProperty("接受者的id")
    @TableField("receiver_id")
    private Integer receiverId;

    @ApiModelProperty("消息内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("发送消息的时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("消息状态1已读 2 未读")
    @TableField("status")
    private Boolean status;
}
