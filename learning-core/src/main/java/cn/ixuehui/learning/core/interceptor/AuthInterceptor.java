package cn.ixuehui.learning.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.ixuehui.learning.core.annotation.NoAuth;
import cn.ixuehui.learning.core.annotation.NoAuth.AuthType;
import cn.ixuehui.learning.core.constants.Constants;
import cn.ixuehui.learning.core.exception.TokenErrorException;
import cn.ixuehui.learning.core.exception.UnLoginException;
import cn.ixuehui.learning.core.redis.RedisOperator;
import cn.ixuehui.learning.core.util.InviteCodeUtil;

/**
 * 登录拦截器
 * @author szq
 * Date: 2019年1月9日 上午9:17:05
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	/*访客token*/	
	@Value("${visitorToken}")
	private String visitorToken;
	
	@Autowired
	private RedisOperator redisOperator;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}
				
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		//获取token
		String loginToken = request.getParameter(Constants.USER_LOGIN_TOKEN);
		if (clazz.isAnnotationPresent(NoAuth.class) || method.isAnnotationPresent(NoAuth.class)) {
			NoAuth noAuth = method.getAnnotation(NoAuth.class);
			AuthType type = noAuth.authType();
			logger.info("类："+clazz.getName()+"或"+method.getName()+"允许访问的用户类型为：" + type);
			if (AuthType.anyone.equals(type)) { //不需要登录即可访问
				return true;
			}else if (AuthType.visitor.equals(type)) { //需要访客模式才可访问
				if (StringUtils.isEmpty(loginToken)) {
					throw new UnLoginException();
				}else if (visitorToken.equals(loginToken)) {//游客模式
					return true;
				}else if (!visitorToken.equalsIgnoreCase(loginToken) && visitorToken.length() == 38) {//用户已经登录
					//判断token是否存在
					String redisKey = Constants.REDIS_KEY + InviteCodeUtil.codeToId(loginToken.substring(32));
					Object token = redisOperator.get(redisKey);
					if (token!=null && loginToken.equalsIgnoreCase(token.toString())) {
						return true;
					}else {
						throw new TokenErrorException();
					}
				}
			}
		}else { //需要登陆之后才能访问
			if (StringUtils.isEmpty(loginToken)) {
				throw new UnLoginException();
			}
			long userId = InviteCodeUtil.codeToId(loginToken.substring(32));
			String redisKey = Constants.REDIS_KEY + userId;
			Object token = redisOperator.get(redisKey);
			if (loginToken.length()!= 38 || visitorToken.equalsIgnoreCase(loginToken)
					||token==null || !loginToken.equalsIgnoreCase(token.toString())) {
				throw new TokenErrorException();
			}else {
				request.setAttribute(Constants.CURRENT_USER_ID, userId);
				return true;
			}
		}
		return true;
	}

	
}
