package com.cve.test.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class SecurityUtils {

    public static SessionUser getSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        if(authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof SessionUser){
            return (SessionUser) authentication.getPrincipal();
        }
        return null;
    }

    public static String getOpenId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        if(authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof SessionUser){
            return ((SessionUser) authentication.getPrincipal()).getOpenId();
        }
        return null;
    }

    public static Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
        if(authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof SessionUser){
            return ((SessionUser) authentication.getPrincipal()).getUserId();
        }
        return null;
    }


    public static boolean hasRole(String role){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        if(CollectionUtils.isEmpty(authorities)){
            return false;
        }
        return authorities.contains(new SimpleGrantedAuthority(role));
    }

    public static String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        if(CollectionUtils.isEmpty(authorities)){
            return null;
        }
        for(GrantedAuthority grantedAuthority : authorities){
            return grantedAuthority.getAuthority();
        }
        return null;
    }
}
