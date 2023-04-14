package com.cve.test.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author : Kevin.
 * @version 0.1 : UsernamePasswordAuthenticationProvider v0.1 2019/4/17 10:36 By Kevin.
 * @description :
 */
public class UsernamePasswordAuthenticationProvider extends AbstractCustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

//        User user = userManager.checkUsername(username);
//        if (!BCrypt.checkpw(password, user.getPassword())) {
//            throw new BizCoreException("用户名或密码不正确");
//        }
        SessionUser sessionUser = new SessionUser("", 1L, "", "");
        return new UsernamePasswordAuthenticationToken(sessionUser, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
