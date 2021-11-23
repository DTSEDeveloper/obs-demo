package com.huaweicloud;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.huaweicloud.commons.pojo.User;
import com.huaweicloud.dtse.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class test {

    User user = new User();

    @Autowired
    private UserService userService;

    @Test
    public void saveUser() {

        //1、初始化对象参数
        String id = (String) IdWorker.getIdStr();
        String mobile = "15927356938";
        String username = "dtse";
        String password = "dtse";

        user.setId(id);
        user.setMobile(mobile);
        user.setUsername(username);
        user.setPassword(password);

        //2、调用UserService 的save方法保存对象至数据库中。
        userService.save(user);
    }

}

