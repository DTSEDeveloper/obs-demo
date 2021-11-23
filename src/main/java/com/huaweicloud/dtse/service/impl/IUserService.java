package com.huaweicloud.dtse.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huaweicloud.commons.pojo.User;

public interface IUserService extends IService<User> {

    boolean update(User user);

}
