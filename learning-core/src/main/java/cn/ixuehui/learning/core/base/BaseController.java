package cn.ixuehui.learning.core.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import cn.ixuehui.learning.core.constants.Constants;

public abstract class BaseController {

	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	private static final String TOKEN = "Authorization";
	
	private static final String TOKEN_HEAD = "Bearer ";
	
	@Autowired
	void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 获取当前登录用户Id
	 * 在拦截器中已经验证过，可以放心使用，不会Null指针异常
	 */
	protected Object getCurrentUserId(){
		return request.getAttribute(Constants.CURRENT_USER_ID);
	}
	
	
	/**
	 * 获取token
	 * @return
	 */
	public String getHeader() {
		String token = "";
		String header = request.getHeader(TOKEN);
		if (header != null && header.startsWith(TOKEN_HEAD)) {
			token = header.replace(TOKEN_HEAD, "");
		}
		return token;
	}
	
	/**
	 * 响应头消息中添加token
	 * @param token
	 */
	public void setToken(String token) {
		response.setHeader(TOKEN, TOKEN_HEAD + token);
	}

}
