package com.huaweicloud.dtse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaweicloud.commons.pojo.User;
import com.huaweicloud.dtse.mapper.UserMapper;
import com.huaweicloud.dtse.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * user service接口
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public boolean save(User user) {

        userMapper.insert(user);

        return true;
    }

    public boolean update(User user) {
        //创建 查询条件构造器
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", user.getId());
        userMapper.update(user, queryWrapper);

        return true;
    }

    public User findByMobile(String mobile) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", mobile);

        return userMapper.selectOne(queryWrapper);
    }

}
