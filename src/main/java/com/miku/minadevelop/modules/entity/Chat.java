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
@TableName("cc_chat")
@ApiModel(value = "Chat对象", description = "Chat对象")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("发送用户的id")
    @TableField("send_uid")
    private Integer sendUid;

    @ApiModelProperty("接收用户的id")
    @TableField("receiver_uid")
    private Integer receiverUid;

    @ApiModelProperty("发送时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("最后一条消息的id")
    @TableField("last_message_id")
    private Integer lastMessageId;
}
