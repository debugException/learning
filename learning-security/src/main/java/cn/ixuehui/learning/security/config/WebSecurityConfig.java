package cn.ixuehui.learning.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import cn.ixuehui.learning.security.config.handler.AuthFailureHandler;
import cn.ixuehui.learning.security.config.handler.AuthSuccessHandler;
import cn.ixuehui.learning.security.config.handler.NoAuthAccessDeniedHandler;
import cn.ixuehui.learning.security.filter.JWTAuthenticationFilter;

/**
 * spring Security配置安全控制中心
 * @author szq
 * Date: 2019年1月8日 下午1:03:38
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	/**依赖注入自定义的登陆成功处理器*/
	@Autowired
	private AuthSuccessHandler authSuccessHandler;
	
	/**依赖注入自定义的登录失败处理器*/
	@Autowired
	private AuthFailureHandler authFailureHandler;
	
	/**依赖注入自定义的未授权处理器*/
	@Autowired
	private NoAuthAccessDeniedHandler noAuthAccessDeniedHandler;
	
	/**注入我们自己的登录逻辑验证器*/
	@Autowired
	private MyAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll() //白名单，不需要登录就可访问
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                .formLogin() 
                .loginProcessingUrl("/login") //用于指定前后端分离的时候调用后台登录接口的名称 默认"/login"
                .successHandler(authSuccessHandler) //配置登陆成功的自定义处理类
                .failureHandler(authFailureHandler) //配置登录失败的自定义处理类
                .and()
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(noAuthAccessDeniedHandler);  //配置没有权限的自定义处理类
    }
	

	/**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {"/tasks/**", "/druid/**", "/user/login", "/user/register", "/login"};
    

}
