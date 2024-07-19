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
@TableName("cc_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("用户账号")
    @TableField("account")
    private String account;

    @ApiModelProperty("用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("逻辑删除")
    @TableField("is_valid")
    private Integer isValid;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
}
