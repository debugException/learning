package cn.ixuehui.learning.security.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.ixuehui.learning.dao.entity.User;
import lombok.Data;

@Data
public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String username;
	
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	// 账号是否未过期，默认是false，记得要改一下
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 账号是否未锁定，默认是false，记得也要改一下
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 账号凭证是否未过期，默认是false，记得还要改一下
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public JwtUser() {}

	public JwtUser(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));;
	}

	@Override
	public String toString() {
		return "JwtUser [id=" + id + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + "]";
	}
	

}
