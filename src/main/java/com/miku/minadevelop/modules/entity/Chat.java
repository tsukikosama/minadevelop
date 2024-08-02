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
@TableName("cc_chat")
@ApiModel(value = "Chat对象", description = "Chat对象")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("发送用户的id")
    @TableField("send_uid")
    private String sendUid;

    @ApiModelProperty("接收用户的id")
    @TableField("receiver_uid")
    private String receiverUid;

    @ApiModelProperty("发送时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("最后一条消息的id")
    @TableField("last_message_id")
    private String lastMessageId;
}
