package cn.ixuehui.learning.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不需要登录即可访问接口
 * @author szq
 * Date: 2019年1月9日 上午9:10:48
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NoAuth {
	AuthType authType() default AuthType.anyone;
	public enum AuthType{
		visitor, //游客模式，需要携带游客token
		anyone; //不需要携带游客token
	}
}
