package cn.ixuehui.learning.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.ixuehui.learning.core.interceptor.AuthInterceptor;

/**
 * 配置类
 * @author szq
 * Date: 2019年1月8日 下午3:07:57
 */
@Configuration
@Component
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor).excludePathPatterns("/druid/*").addPathPatterns("/**");
	}
	
}
