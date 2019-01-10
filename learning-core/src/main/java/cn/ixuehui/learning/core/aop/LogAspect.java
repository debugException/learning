package cn.ixuehui.learning.core.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.ixuehui.learning.core.annotation.OperationLog;
import cn.ixuehui.learning.core.async.AsyncTask;
import cn.ixuehui.learning.core.constants.Constants;
import cn.ixuehui.learning.core.mongodb.VisitLog;
import cn.ixuehui.learning.core.util.CommonUtil;
import cn.ixuehui.learning.core.util.InviteCodeUtil;
import cn.ixuehui.learning.core.util.JsonUtils;

/**
 * 日志切面
 * @author szq
 * Date: 2019年1月9日 下午2:05:23
 */
@Aspect
@Order(1)
@Component
public class LogAspect {

	private static Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Autowired
	private AsyncTask asyncTask;
			
	@Pointcut("@annotation(cn.ixuehui.learning.core.annotation.OperationLog)")    
	public void lognoteAspect() {    
	}   
	
	@Around("lognoteAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		VisitLog visitLog = getVisitLog(pjp);
			
		MethodSignature methodSignature =(MethodSignature)pjp.getSignature();      
		OperationLog operationLog = methodSignature.getMethod().getAnnotation(OperationLog.class); 
		if(operationLog != null){
			visitLog.setDescription(operationLog.description());
			visitLog.setModule(operationLog.key());
		}
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String loginToken = request.getParameter(Constants.USER_LOGIN_TOKEN);
		if (!StringUtils.isEmpty(loginToken)) {
			int userId = (int) InviteCodeUtil.codeToId(loginToken.substring(32));
			visitLog.setUserId(userId);
			visitLog.setLoginToken(loginToken);
		}
		visitLog.setStartDate(new Date());
		String ip = getIpAddr(request);
		visitLog.setIp(ip);
		long start = System.currentTimeMillis();
		Object object = null;
		try {
			object = pjp.proceed();
		} catch (Exception e) {
			visitLog.setResult("error: " + e.getMessage());
		}
		
		visitLog.setEndDate(new Date());
		long end = System.currentTimeMillis();
		visitLog.setExcedate((int)(end - start));
		if(StringUtils.isEmpty(visitLog.getResult())) {
			visitLog.setResult(JsonUtils.objToJson(object));
		}
		
		logger.debug("访问者：" + visitLog.getUserId());
		logger.debug("访问方法：" + visitLog.getClassName() + "." + visitLog.getMethodName());
		logger.debug("访问参数：" + visitLog.getArgJson());
		logger.debug("耗时：" + visitLog.getExcedate());
		logger.debug("返回值：" + visitLog.getResult());
		
		//异步保存日志
		asyncTask.saveLog(visitLog);
		
		return object;
		
	}
	
	/**保存日志到MongoDB数据库中*//*
	@Async
	public void saveLog(VisitLog visitLog) {
		System.out.println("333333333333333333333333");
		MongodbOperator.save(visitLog);
		System.out.println("444444444444444444444");

	}*/
	
	/**封装日志类*/
	private VisitLog getVisitLog(JoinPoint joinPoint) {
		VisitLog visitLog = new VisitLog();
		visitLog.setXh(CommonUtil.getUUID());
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Method[] methods = joinPoint.getClass().getMethods();
		for(Method method: methods) {
			if (method.getName().equals(methodName)) {
				OperationLog operationLog = method.getAnnotation(OperationLog.class);
				if (operationLog!=null) {
					visitLog.setDescription(operationLog.description());
					visitLog.setModule(operationLog.key());
				}
			}
		}
		
		List<Object> list = Arrays.asList(arguments);
		visitLog.setClassName(targetName);
		visitLog.setMethodName(methodName);
		visitLog.setArgJson(JsonUtils.objToJson(list));
		return visitLog;
	}
	
	/**获取访问ip*/
	private String getIpAddr(HttpServletRequest request) throws Exception{  
		String ip = request.getHeader("X-Real-IP");  
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
			return ip;  
		}  
		ip = request.getHeader("X-Forwarded-For");  
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
			// 多次反向代理后会有多个IP值，第一个为真实IP。  
			int index = ip.indexOf(',');  
			if (index != -1) {  
				return ip.substring(0, index);  
			} else {  
				return ip;  
			}  
		} else {  
			return request.getRemoteAddr();  
		}  
	}
	
}
