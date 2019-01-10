package cn.ixuehui.learning.security.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 实现自己的AuthenticationProvider类，用来自定义用户校验机制
 * @author szq
 * Date: 2019年1月8日 上午8:54:53
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取表单输入中返回的用户名;
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码；
        String password = (String) authentication.getCredentials();
        // 这里调用我们的自己写的获取用户的方法；
        UserDetails userInfo = userDetailsService.loadUserByUsername(userName);
        if (userInfo == null) {
            throw new BadCredentialsException("用户名不存在");
        }

        // 这里我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!DigestUtils.md5Hex((String)password).equals(userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//这里直接改成retrun true;表示是支持这个执行
		return true;
	}

}
