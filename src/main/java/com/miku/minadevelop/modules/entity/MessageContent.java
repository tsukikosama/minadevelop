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
 * @since 2024-07-18
 */
@Getter
@Setter
@TableName("cc_message_content")
@ApiModel(value = "MessageContent对象", description = "")
public class MessageContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "msg_id", type = IdType.AUTO)
    private Integer msgId;

    @ApiModelProperty("消息内容")
    @TableField("msg_content")
    private String msgContent;

    @ApiModelProperty("消息发送时间")
    @TableField("msg_time")
    private LocalDateTime msgTime;

    @ApiModelProperty("消息状态")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("发送人的uid")
    @TableField("msg_send")
    private Integer msgSend;

    @ApiModelProperty("接收人的uid")
    @TableField("msg_reciver")
    private Integer msgReciver;
}
