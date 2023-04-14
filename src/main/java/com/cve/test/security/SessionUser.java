package com.cve.test.security;


import lombok.Getter;
import lombok.Setter;

import java.security.Principal;

/**
 * @description:
 * @author: Forst
 * @create: 2018-09-06 15:31
 **/

@Getter
@Setter
public class SessionUser implements Principal {

    private String name;

    public SessionUser(String name, Long userId, String openId, String role){
        this.name = name;
        this.userId = userId;
        this.openId = openId;
        this.role = role;
    }

    @Override
    public String getName() {
        return name;
    }

    private Long userId;

    private String openId;

    private String role;
}
