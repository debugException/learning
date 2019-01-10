package cn.ixuehui.learning.core.exception;

/**
 * 账号不存在异常
 * @author szq
 * Date: 2019年1月8日 下午3:23:22
 */

public class UnAccountException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnAccountException(String msg) {
        super(msg);
    }

    public UnAccountException() {
        super();
    }
}
