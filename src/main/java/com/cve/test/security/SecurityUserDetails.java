package com.cve.test.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class SecurityUserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SecurityUserDetails(String username, String password, String salt, Long userId, Long tenantId,
							   boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, false, false, accountNonLocked, authorities);
		this.userId = userId;
		this.tenantId = tenantId;
		this.salt = salt;
	}

	private String salt;

	private Long userId;

	private Long tenantId;

	private Integer level;
}
