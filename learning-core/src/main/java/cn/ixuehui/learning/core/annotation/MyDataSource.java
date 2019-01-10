package cn.ixuehui.learning.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源注解
 * @author szq
 * Date: 2019年1月9日 下午4:10:57
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyDataSource {

    DBTypeEnum value() default DBTypeEnum.master;
    
    public enum DBTypeEnum {

    	master("master"), common("common");
        private String value;

        DBTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}
