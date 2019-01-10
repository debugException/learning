package cn.ixuehui.learning.core.exception;

/**
 * 密码错误异常
 * @author szq
 * Date: 2019年1月8日 下午3:22:47
 */
public class PasswordErrorException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PasswordErrorException(String msg) {
        super(msg);
    }

    public PasswordErrorException() {
        super();
    }
}
