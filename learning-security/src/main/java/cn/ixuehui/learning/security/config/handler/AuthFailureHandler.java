package cn.ixuehui.learning.security.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.ixuehui.learning.core.base.ResultData;

/**
 * 处理登录验证失败的类
 * @author szq
 * Date: 2019年1月8日 上午9:35:24
 */
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

	 /**Json转化工具*/
    @Autowired
    private ObjectMapper objectMapper;
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println("用户" + request.getParameter("username") + "登录失败，失败原因是：" + exception.getMessage());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ResultData.failure(500, exception.getMessage())));
	}

}
