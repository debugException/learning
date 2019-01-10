package cn.ixuehui.learning.core.exception;

/**
 * 接口404
 * @author szq
 * Date: 2018年12月28日 上午10:07:45
 */
public class HandleNotFoundException extends  BaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HandleNotFoundException(){}

    public HandleNotFoundException(String msg){
        super(msg);
    }
}
