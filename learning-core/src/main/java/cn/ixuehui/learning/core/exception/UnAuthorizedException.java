package cn.ixuehui.learning.core.exception;

/**
 * 未授权异常
 * @author szq
 * Date: 2019年1月8日 下午3:23:52
 */

public class UnAuthorizedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnAuthorizedException(String msg) {
        super(msg);
    }

    public UnAuthorizedException() {
        super();
    }
}
