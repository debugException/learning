package cn.ixuehui.learning.security.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.ixuehui.learning.core.base.Record;
import cn.ixuehui.learning.core.base.ResultData;
import cn.ixuehui.learning.security.entity.JwtUser;
import cn.ixuehui.learning.security.jwt.JwtTokenUtils;

/**
 * 处理登录验证成功的类
 * @author szq
 * Date: 2019年1月8日 上午8:43:35
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	 /**Json转化工具*/
    @Autowired
    private ObjectMapper objectMapper;
    
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		System.out.println("用户" + jwtUser.getUsername() + "登录成功");
		String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
		// 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
		response.setContentType("application/json;charset=UTF-8");
		Record<String, Object> record = new Record<>();
		record.put("id", jwtUser.getId());
		record.put("username", jwtUser.getUsername());
		response.getWriter().write(objectMapper.writeValueAsString(ResultData.success(record)));
	}

}
