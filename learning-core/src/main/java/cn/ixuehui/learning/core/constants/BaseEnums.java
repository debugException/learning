package cn.ixuehui.learning.core.constants;

import java.util.HashMap;
import java.util.Map;

import cn.ixuehui.learning.core.base.BaseEnum;

/**
 * 请求结果枚举值
 * @author szq
 * Date: 2018年12月28日 上午10:03:42
 */
public enum BaseEnums implements BaseEnum<Integer, String>{

	SUCCESS(200, "请求成功"),

    FORBIDDEN(403, "无权限访问"),

    NOT_FOUND(404, "请求资源不存在"),

    PARAMETER_NOT_NULL(405, "参数不能为空"),

    FAILURE(500, "请求失败"),

    ERROR(505, "系统异常"),

    UNLOGIN(901, "用户未登录"),

    INVALID(903, "TOKEN错误"),
	
	PASSWORDERR(905, "密码错误");
	

    private Integer code;

    private String desc;
    
    
    private static Map<Integer, String> allMap = new HashMap<>();

    BaseEnums(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    static{
        for(BaseEnums enums : BaseEnums.values()){
            allMap.put(enums.code, enums.desc);
        }
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }

}
