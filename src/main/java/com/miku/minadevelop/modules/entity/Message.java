package com.miku.minadevelop.modules.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
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
 * @since 2024-07-19
 */
@Getter
@Setter
@TableName("cc_message")
@Data
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("消息id")
    @TableField(value = "message_id")
    private String messageId;

    @ApiModelProperty("发送者的id")
    @TableField("send_uid")
    private String sendUid;

    @ApiModelProperty("接受者的id")
    @TableField("receiver_uid")
    private String receiverUid;

    @ApiModelProperty("消息内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("发送消息的时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;
    @ApiModelProperty("消息状态1已读 2 未读")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("聊天关系的id")
    @TableField("chat_id")
    private String chatId;
}
