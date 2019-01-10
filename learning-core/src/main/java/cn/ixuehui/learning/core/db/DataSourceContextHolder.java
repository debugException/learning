package cn.ixuehui.learning.core.db;

import cn.ixuehui.learning.core.annotation.MyDataSource.DBTypeEnum;

/**
 * 设置获取数据源
 * @author szq
 * Date: 2019年1月9日 下午3:52:34
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<Object> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param dbTypeEnum
     */
    public static void setDbType(DBTypeEnum dbTypeEnum) {
        contextHolder.set(dbTypeEnum.getValue());
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
    }
}
