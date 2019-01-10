package cn.ixuehui.learning.core.exception;

/**
 * 用户未登录异常
 * @author szq
 * Date: 2019年1月8日 下午3:22:26
 */
public class UnLoginException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnLoginException(String msg) {
        super(msg);
    }

    public UnLoginException() {
        super();
    }
}
