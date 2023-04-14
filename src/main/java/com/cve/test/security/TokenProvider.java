package com.cve.test.security;

import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${jwt.securityKey}")
    private String secretKey;

    @Value("${jwt.tokenValidityInMinutes}")
    private Long tokenValidityInMinutes;


    private PathMatcher pathMatcher = new AntPathMatcher();


    public String createToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMinutes * 60L * 1000L);
        SessionUser user = (SessionUser) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("open_id", user.getUserId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 拦截请求后进行请求校验
     *
     * @param token
     * @param
     * @return
     */
    public Authentication getAuthentication(String token, HttpServletRequest request) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String openId = String.valueOf(claims.get("open_id", String.class));

            if (ObjectUtil.isNull(openId)) {
                return null;
            }

            SessionUser sessionUser = new SessionUser("", 1L, "", "");
            return new UsernamePasswordAuthenticationToken(sessionUser, null, null);
        } catch (Exception e) {
            log.error("TokenProvider.getAuthentication 异常 token {}, e {}", token, e);
            return null;
        }

    }


    public String resolveToken(HttpServletRequest request) {

        String headerToken = request.getHeader(JWTConfigurer.COOKIE_AUTHORIZATION_NAME);
        if (StringUtils.isNotBlank(headerToken)) {
            return headerToken;
        }

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length <= 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (JWTConfigurer.COOKIE_AUTHORIZATION_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }


}
