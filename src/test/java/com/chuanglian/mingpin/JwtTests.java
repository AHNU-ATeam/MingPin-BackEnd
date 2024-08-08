package com.chuanglian.mingpin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");
        // 生成JWT
        String token = JWT.create()
                .withClaim("user", claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 过期时间2小时
                .sign(Algorithm.HMAC256("mingpin")); // 设置密钥

        System.out.println(token);
    }

    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MjMwNTMyMjF9." +
                "4ABYZO1jDMcLYjchhGn-ahAu_eFuD_k27OOGx24W1Qk";
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mingpin")).build();
        DecodedJWT decodedJWT = verifier.verify(token); // 解析
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
