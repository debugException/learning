package cn.ixuehui.learning.core.exception;

/**
 * Service层异常
 * @author szq
 * Date: 2018年12月28日 上午10:07:36
 */
public class ServiceException extends BaseException {

    private static final long serialVersionUID = 6058294324031642376L;

    public ServiceException(){}

    public ServiceException(String message){
        super(message);
    }
}
