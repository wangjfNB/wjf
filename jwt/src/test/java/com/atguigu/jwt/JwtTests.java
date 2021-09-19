package com.atguigu.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class JwtTests {

    //过期时间，毫秒，24小时
    private static long tokenExpiration = 24*60*60*1000;
    //秘钥
    private static String tokenSignKey = "atguigu123";

    @Test
    public void testCreateToken() {
        JwtBuilder jwtBuilder = Jwts.builder();
        //头、载荷、签名哈希
        String jwtToken =jwtBuilder
                //头
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")

                //载荷:自定义信息
                .claim("nickname", "Helen")
                .claim("avatar", "1.jpg")
                .claim("role","admin")

                //载荷：默认信息
                .setSubject("guli-user") //令牌主题
                .setIssuer("atguigu")//签发者
                .setAudience("atguigu")//接收者
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) //过期时间
                .setNotBefore(new Date(System.currentTimeMillis() + 20*1000)) //20秒后可用
                .setId(UUID.randomUUID().toString())

                //签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)//签名哈希
                .compact(); //转换成字符串

        System.out.println(jwtToken);
    }

    @Test
    public void testGetUserInfo(){
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWNrbmFtZSI6IkhlbGVuIiwiYXZhdGFyIjoiMS5qcGciLCJyb2xlIjoiYWRtaW4iLCJzdWIiOiJndWxpLXVzZXIiLCJpc3MiOiJhdGd1aWd1IiwiYXVkIjoiYXRndWlndSIsImlhdCI6MTYyOTA5NzA4NCwiZXhwIjoxNjI5MTgzNDg0LCJuYmYiOjE2MjkwOTcxMDQsImp0aSI6IjQ1NmEyMjdhLTYxMzItNDdiZS1hOThkLWNmZDNhNjZlZTNhNiJ9.iWtNGjPNt5Hogi_PDRdjxrlSb5vEAqbxqsMwI2gZQMg";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        String role = (String)claims.get("role");

        System.out.println(nickname);
        System.out.println(avatar);
        System.out.println(role);

        String id = claims.getId();
        System.out.println(id);
    }
}
