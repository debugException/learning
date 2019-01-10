package cn.ixuehui.learning.dao.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 *  配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
 * @author szq
 * Date: 2018年12月28日 下午1:11:06
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

	@Override
    public void insertFill(MetaObject metaObject) {

        Object createTime = getFieldValByName("createTime", metaObject);
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (createTime == null)
            setFieldValByName("createTime",new Date(), metaObject);
        if (updateTime == null)
            setFieldValByName("updateTime",new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }

	

}
