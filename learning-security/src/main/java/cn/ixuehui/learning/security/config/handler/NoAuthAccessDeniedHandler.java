package cn.ixuehui.learning.security.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.ixuehui.learning.core.base.ResultData;
import cn.ixuehui.learning.core.constants.BaseEnums;

/**
 * 处理没有权限的类
 * @author szq
 * Date: 2019年1月8日 上午10:10:56
 */
@Component
public class NoAuthAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResultData.failure(BaseEnums.FORBIDDEN.code(), BaseEnums.FORBIDDEN.desc())));
		
	}

}
