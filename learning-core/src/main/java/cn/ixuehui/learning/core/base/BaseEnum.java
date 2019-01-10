package cn.ixuehui.learning.core.base;

/**
 * 基础枚举类
 * @author szq
 * Date: 2018年12月28日 上午10:02:33
 * @param <K>
 * @param <V>
 */
public interface BaseEnum<K, V> {

	/**
     * 获取编码
     * @return
     */
    K code();

    /**
     * 获取描述
     * @return
     */
    V desc();
}
