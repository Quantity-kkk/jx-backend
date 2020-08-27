package top.kyqzwj.wx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/8/25 13:39
 */
public class JwtTokenUtil {

    /**
     * 加密解密盐值
     * */
    private static final String SALT = "kyqzwj0915";

    /**
     * 生成token(请根据自身业务扩展)
     * @param username （主体信息）
     * @param expirationSeconds 过期时间（秒）
     * @param claims 自定义身份信息
     * @return
     */
    public static String generateToken(String username, int expirationSeconds, Map<String,Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                //主题
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, SALT)
                .compact();
    }

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String generateToken(String user){
        Map<String, Object> map = new HashMap<>(16);
        map.put("avatar","../../images/index-list/avatar/7.jpg");
        map.put("nick_name","奇思妙想");
        map.put("id","ZXJDKXS");
        return Jwts.builder()
                .setId(user)
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()))
                .setIssuedAt(new Date())
                .setIssuer("ZBB")
                .signWith(SignatureAlgorithm.HS512, SALT)
                .compact();
    }

    /**
     * 解析token,获得subject中的信息
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        String subject = null;
        try {
            subject = getTokenBody(token).getSubject();
        } catch (Exception e) {
        }
        return subject;
    }

    /**
     * 获取token自定义属性
     * @param token
     * @return
     */
    public static Map<String,Object> getClaims(String token){
        Map<String,Object> claims = null;
        try {
            claims = getTokenBody(token);
        }catch (Exception e) {
        }

        return claims;
    }


    /**
     * 解析token
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SALT)
                .parseClaimsJws(token)
                .getBody();
    }
}
