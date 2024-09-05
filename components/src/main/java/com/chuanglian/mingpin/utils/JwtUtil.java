package com.chuanglian.mingpin.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {

    public static String generateJwt(Map<String, Object> claims) {
        // TODO 改变了token过期时间

        // 生成JWT
        return JWT.create()
                .withClaim("user", claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30)) // 过期时间2小时
                .sign(Algorithm.HMAC256("com/chuanglian/mingpin")); // 设置密钥
    }

    public static DecodedJWT parseJwt(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("com/chuanglian/mingpin")).build();
        return verifier.verify(token); // 返回解析器
    }
}