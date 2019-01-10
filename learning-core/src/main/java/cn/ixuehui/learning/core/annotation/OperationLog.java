package cn.ixuehui.learning.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author szq
 * Date: 2019年1月9日 下午2:12:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

	String key() default ""; //方法名
	String description() default ""; //方法描述
}
