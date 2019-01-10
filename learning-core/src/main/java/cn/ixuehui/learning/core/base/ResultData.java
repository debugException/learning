package cn.ixuehui.learning.core.base;

/**
 * Result生成工具类
 * 
 * @author szq Date: 2018年12月28日 上午9:53:26
 */
public class ResultData extends Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ResultData() {
	}

	public static Result newResult() {
		return new Result();
	}

	public static Result newResult(boolean success) {
		return new Result(success);
	}

	// 业务调用成功
	public static Result success() {
		return new Result();
	}

	public static Result success(String msg) {
		return new Result(true, 200, msg);
	}

	public static Result success(Object data) {
		return new Result(true, 200, data);
	}

	public static Result success(Object data, String msg) {
		return new Result(true, 200, msg, data);
	}

	// 业务调用失败
	public static Result failure() {
		return new Result(false);
	}

	public static Result failure(Integer status, String msg) {
		return new Result(false, status, msg);
	}
}
