package cn.ixuehui.learning.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import cn.ixuehui.learning.core.base.Result;
import cn.ixuehui.learning.core.base.ResultData;
import cn.ixuehui.learning.core.constants.BaseEnums;
import cn.ixuehui.learning.core.exception.BaseException;
import cn.ixuehui.learning.core.exception.PasswordErrorException;
import cn.ixuehui.learning.core.exception.ServiceException;
import cn.ixuehui.learning.core.exception.TokenErrorException;
import cn.ixuehui.learning.core.exception.UnAuthorizedException;
import cn.ixuehui.learning.core.exception.UnLoginException;

/**
 * 全局异常处理
 * @author szq
 * Date: 2018年12月28日 上午10:06:26
 */
@RestControllerAdvice
public class GlobalExceptionConfig {

	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);

	    /**
	     * 处理 ServiceException 异常
	     */
	    @ExceptionHandler(ServiceException.class)
	    public Result handleServiceException(ServiceException e){
	        Result result = ResultData.failure(BaseEnums.ERROR.code(), e.getMessage());
	        logger.info("ServiceException[code: {}, message: {}]", e.getMessage());
	        return result;
	    }

	    /**
	     * 处理 UnAuthorizedException 异常
	     */
	    @ExceptionHandler(UnAuthorizedException.class)
	    public Result handleAuthorityException(UnAuthorizedException e){
	        Result result = ResultData.failure(BaseEnums.FORBIDDEN.code(), BaseEnums.FORBIDDEN.desc());
	        logger.info("AuthorityException[code: {}, message: {}]", e.getMessage());
	        return result;
	    }

	    /**
	     * 处理 NoHandlerFoundException 异常. <br/>
	     * 需配置 [spring.mvc.throw-exception-if-no-handler-found=true]
	     * 需配置 [spring.resources.add-mappings=false]
	     */
	    @ExceptionHandler(NoHandlerFoundException.class)
	    public Result handleNotFoundException(NoHandlerFoundException e){
	        Result result = ResultData.failure(BaseEnums.NOT_FOUND.code(), BaseEnums.NOT_FOUND.desc());
	        logger.info(e.getMessage());
	        return result;
	    }
	    
	    /**
	     * 处理未登录异常
	     * @param e
	     * @return
	     */
	    @ExceptionHandler(UnLoginException.class)
	    public Result unLoginException(UnLoginException e){
	        Result result = ResultData.failure(BaseEnums.UNLOGIN.code(), BaseEnums.UNLOGIN.desc());
	        logger.info(e.getMessage());
	        return result;
	    }
	    
	    /**
	     * 处理token错误异常
	     * @param e
	     * @return
	     */
	    @ExceptionHandler(TokenErrorException.class)
	    public Result tokenExpiredException(TokenErrorException e){
	        Result result = ResultData.failure(BaseEnums.INVALID.code(), BaseEnums.INVALID.desc());
	        logger.info(e.getMessage());
	        return result;
	    }
	    
	    /**
	     * 处理密码错误异常
	     * @param e
	     * @return
	     */
	    @ExceptionHandler(PasswordErrorException.class)
	    public Result passwordErrorException(PasswordErrorException e){
	        Result result = ResultData.failure(BaseEnums.INVALID.code(), BaseEnums.INVALID.desc());
	        logger.info(e.getMessage());
	        return result;
	    }

	    /**
	     * 处理 BaseException 异常
	     */
	    @ExceptionHandler(BaseException.class)
	    public Result handleBaseException(BaseException e){
	        Result result = ResultData.failure(BaseEnums.FAILURE.code(), e.getMessage());
	        logger.error("BaseException[code: {}, message: {}]", e.getMessage(), e);
	        return result;
	    }

	    /**
	     * 处理 Exception 异常
	     */
	    @ExceptionHandler(Exception.class)
	    public Result handleException(Exception e){
	        Result result = ResultData.failure(BaseEnums.FAILURE.code(), e.getMessage());
	        logger.error(e.getMessage(), e);
	        return result;
	    }
}
