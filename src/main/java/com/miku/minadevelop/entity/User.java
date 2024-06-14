package com.miku.minadevelop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("cc_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

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

    @ApiModelProperty("用户状态 0 正常 1 异常")
    @TableField("status")
    private Boolean status;


}
