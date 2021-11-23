package com.huaweicloud.commons;

import com.huaweicloud.commons.response.ResultCode;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 请求解析类
 */
@Data
@Getter
@Setter
@Component
public class RequestUtil {

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 校验用户请求是否合法
     *
     * @param request HttpServletRequest请求
     * @return 返回 请求用户ID
     */
    public String getUserId(HttpServletRequest request) {

        //1、从 HttpServletRequest中授权头信息
        String authorization = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authorization)) {
            try {
                throw new Exception(String.valueOf(ResultCode.UNAUTHENTICATED));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //2、获取请求头中Token信息
        String token = authorization.replace("Bearer", "");

        //3、Token校验
        Claims claims = jwtUtil.parseToken(token);

        if (claims == null) {
            try {
                throw new Exception(String.valueOf(ResultCode.UNAUTHENTICATED));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return claims.getId();
    }

}
