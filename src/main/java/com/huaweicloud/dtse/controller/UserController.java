package com.huaweicloud.dtse.controller;

import com.huaweicloud.commons.JwtUtil;
import com.huaweicloud.commons.RequestUtil;
import com.huaweicloud.commons.pojo.User;
import com.huaweicloud.commons.response.ProfileResult;
import com.huaweicloud.commons.response.Result;
import com.huaweicloud.commons.response.ResultCode;
import com.huaweicloud.dtse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理接口，如用户登录、用户信息获取
 */

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RequestUtil requestUtil;
    @Autowired
    private UserService userService;

    /**
     * 用户登录校验，并返回Token
     *
     * @param loginMap 登陆请求body参数；
     * @return 返回登陆状态；
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginMap) {

        //1、校验用户联系方式 和 用户名密码
        Map<String, String> map = new HashMap<>();
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);

        //2、判断是否存在
        if (user == null || !user.getPassword().equals(password)) {
            //(1)若用户不存在，返回失败
            return new Result(ResultCode.FAIL);
        } else {
            //(2)若用户存在，生产Token,并返回Token
            map.put("mobile", user.getMobile());
            map.put("username", user.getUsername());
            map.put("iconURL", user.getIconURL());
            String token = jwtUtil.getToken(user.getId(), map);

            return new Result(ResultCode.SUCCESS, token);
        }
    }

    /**
     * 获取用户个人信息
     *
     * @param request HttpServletRequest 请求参数；
     * @return 个人信息获取参数；
     */
    @PostMapping("/profile")
    public Result profile(HttpServletRequest request) {

        String id = null;
        try {
            //1、获取用户请求ID
            id = requestUtil.getUserId(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2、通过id，获取用户名
        User user = userService.getById(id);

        //3、返回用户获取结果
        return new Result(ResultCode.SUCCESS, new ProfileResult(user));
    }

}
