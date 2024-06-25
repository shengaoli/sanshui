package com.example.snashuitraverl.demos.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表

 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 支付宝授权ID
     */
    @TableId
    private String openID;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码

     */
    private String password;

    /**
     * 身份证
     */
    private String userCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0 普通用户
1 管理员
2 商家
     */
    private Integer role;

    /**
     * 状态
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 积分
     */
    private Integer bonus;

    /**
     * 头像
     */
    private String avatural;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}