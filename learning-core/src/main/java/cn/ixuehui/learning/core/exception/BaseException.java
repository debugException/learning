package cn.ixuehui.learning.core.exception;

/**
 * 
 * @author szq
 * Date: 2018年12月28日 上午10:08:22
 */

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -997101946070796354L;

    public BaseException(){}

    public BaseException(String message){
        super(message);
    }

}
