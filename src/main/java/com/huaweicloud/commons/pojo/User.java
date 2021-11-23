package com.huaweicloud.commons.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;


/* 数据库初始化脚本
CREATE TABLE `bs_user` (
        `id` varchar(40) NOT NULL COMMENT 'ID',
        `mobile` varchar(40) NOT NULL COMMENT '手机号码',
        `username` varchar(255) NOT NULL COMMENT '用户名称',
        `password` varchar(255) DEFAULT NULL COMMENT '密码',
        `iconURl` varchar(255) DEFAULT NULL COMMENT '用户icon url',
        PRIMARY KEY (`id`),
        UNIQUE KEY `idx_user_phone` (`mobile`) USING BTREE
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
       */

/**
 * User对象类
 * <p>
 * 用户id：id
 * 用户电话：mobile
 * 用户名：username
 * 用户密码：password
 * 用户个人icon：iconURL
 */
@Entity
@TableName("bs_user")
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 4297464181093070302L;
    /**
     * ID
     */
    @javax.persistence.Id
    private String id;
    /**
     * 手机号码
     */

    @TableField(value = "mobile")
    private String mobile;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户icon
     */
    @TableField(value = "iconURL")
    private String iconURL;
}
