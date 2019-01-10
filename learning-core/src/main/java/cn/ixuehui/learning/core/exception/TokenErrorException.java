package cn.ixuehui.learning.core.exception;

/**
 * token错误
 * @author szq
 * Date: 2019年1月8日 下午3:23:05
 */
public class TokenErrorException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TokenErrorException(String msg) {
        super(msg);
    }

    public TokenErrorException() {
        super();
    }
}
