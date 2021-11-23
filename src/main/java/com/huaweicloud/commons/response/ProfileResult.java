package com.huaweicloud.commons.response;

import com.huaweicloud.commons.pojo.User;
import lombok.Data;

/**
 * 用户信息响应类
 * 用户名: username;
 * 用户联系方式: mobile;
 * 个人icon存放地址: iconURL;
 */
@Data
public class ProfileResult {

    private String username;
    private String mobile;
    private String iconURL;

    /**
     * @param user 用户对象参数
     */
    public ProfileResult(User user) {

        this.iconURL = user.getIconURL();
        this.mobile = user.getMobile();
        this.username = user.getUsername();
    }

}
