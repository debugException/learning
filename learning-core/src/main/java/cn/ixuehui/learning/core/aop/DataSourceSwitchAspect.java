package cn.ixuehui.learning.core.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.ixuehui.learning.core.annotation.MyDataSource;
import cn.ixuehui.learning.core.db.DataSourceContextHolder;


/**
 * 数据源切换
 * @author szq
 * Date: 2019年1月9日 下午4:05:34
 */
@Component
@Aspect
@Order(-100) //这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
public class DataSourceSwitchAspect {

	private static Logger logger = LoggerFactory.getLogger(DataSourceSwitchAspect.class);
	
	@Pointcut("@annotation(cn.ixuehui.learning.core.annotation.MyDataSource)")
	public void pointCut() {}
	
	@Before("pointCut() && @annotation(myDataSource)")
	public void doBefore(MyDataSource myDataSource) {
		logger.info("选择数据源：" + myDataSource.value().getValue());
		DataSourceContextHolder.setDbType(myDataSource.value());
	}
	
	@After("pointCut()")
	public void doAfter() {
		DataSourceContextHolder.clearDbType();
	}
}
