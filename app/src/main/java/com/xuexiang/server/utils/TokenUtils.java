/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.server.utils;

import com.xuexiang.constant.TimeConstants;
import com.xuexiang.xutil.security.EncodeUtils;
import com.yanzhenjie.andserver.http.HttpRequest;

import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author xuexiang
 * @since 2020/9/1 12:01 AM
 */
public final class TokenUtils {

    private TokenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 签名秘钥
     */
    public static final String SECRET = "xuexiangjys";

    /**
     * 生成token
     *
     * @param id 一般传入userName
     * @return
     */
    public static String createJwtToken(String id) {
        String issuer = "www.github.com";
        String subject = "xuexiangjys@163.com";
        // 30秒有效
        long ttlMillis = 30 * TimeConstants.SEC;
        return createJwtToken(id, issuer, subject, ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id        编号
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param subject   该JWT所面向的用户，是否使用是可选的；
     * @param ttlMillis 签发时间 （有效时间，过期会报错）
     * @return token String
     */
    public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {
        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = EncodeUtils.base64Decode(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();

    }

    /**
     * Sample method to validate and read the JWT
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(EncodeUtils.base64Decode(SECRET))
                .parseClaimsJws(jwt).getBody();
    }


    /**
     * 从HttpRequest中解析出token
     *
     * @param request 请求
     * @return 请求token
     */
    public static String parseToken(HttpRequest request) {
        String accessToken = request.getHeader("token");
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = request.getParameter("token");
        }
        return accessToken;
    }

}
