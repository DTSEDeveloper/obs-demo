package com.huaweicloud.commons;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Data
@Getter
@Setter
@ConfigurationProperties("jwt.config")
@EnableConfigurationProperties(JwtUtil.class)
@Configuration
/**
 * JWT 认证工具类，Token获取和Token解析
 */
public class JwtUtil {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long ttl;

    /**
     * 签发Token
     *
     * @param id  请求 用户id
     * @param map 关键用户信息保存到map中
     * @return token 返回生产的用户token
     */
    public String getToken(String id, Map<String, String> map) {

        long now = System.currentTimeMillis();
        long exp = now + ttl;

        String token = null;

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date())
                .signWith(key);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        if (ttl > 0) {
            builder.setExpiration(new Date(exp));
        }
        token = builder.compact();
        return token;
    }

    /**
     * 解析Token
     *
     * @param token 用户token
     * @return Claims 返回 jwt Claims
     */
    public Claims parseToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
        return claims;
    }

}
