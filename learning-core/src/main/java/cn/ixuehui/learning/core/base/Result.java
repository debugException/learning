package cn.ixuehui.learning.core.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * 前端返回对象
 * @author szq
 * Date: 2018年12月28日 上午9:51:26
 */
@Data
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 成功与否标志
     */
    private boolean success = true;

    /**
     * 返回状态码，为空则默认200，前端需要拦截一些常见的状态码如404,400,500等等
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;

    /**
     * 相关信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    /**
     * 相关数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public Result(){}

    public Result(boolean success){
        this.success = success;
    }

    public Result(boolean success, Integer status){
        this.success = success;
        this.status = status;
    }

    public Result(boolean success, Integer status, String msg){
        this.success = success;
        this.status = status;
        this.msg = msg;
    }

    public Result(boolean success, Integer status, Object data){
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public Result(boolean success, Integer status, String msg, Object data){
        this.success = success;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

}
