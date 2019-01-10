package cn.ixuehui.learning.core.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import cn.ixuehui.learning.core.config.GlobalExceptionConfig;

public class YunPianUtil {
	// apikey成功注册后登录云片官网,进入后台可查看
	private static final String API_KEY = "41c167af5cbbabe11ea1a325d1b1de7f";

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);
	
	/**
	 * 发送注册验证码
	 * @param code
	 * @param phone
	 * @return
	 */
	public static Integer sendRegCode(String code, String phone) {
		String text = "【学汇教育】正在进行注册操作，您的验证码是" + code ;
		Result<SmsSingleSend> rst = null;
		try {
			YunpianClient client = new YunpianClient(API_KEY).init();
			Map<String, String> param = client.newParam(2);
			param.put(YunpianClient.MOBILE, phone);
			param.put(YunpianClient.TEXT, text);
			rst = client.sms().single_send(param);
			if (rst.getCode() == 0) {
				logger.info("向手机号"+phone+"发送注册账号短信成功！");
			} else {
				logger.info("向手机号"+phone+"发送注册账号短信失败，失败原因为："+rst.getMsg());
			}
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst.getCode();
	}
	
	
	/**
	 * 发送找登录密码验证码
	 * @param code
	 * @param phone
	 * @return
	 */
	public static Integer sendFindPwdCode(String code, String phone) {
		String text = "【学汇教育】正在进行找回密码操作，您的验证码是" + code ;
		Result<SmsSingleSend> rst = null;
		try {
			YunpianClient client = new YunpianClient(API_KEY).init();
			Map<String, String> param = client.newParam(2);
			param.put(YunpianClient.MOBILE, phone);
			param.put(YunpianClient.TEXT, text);
			rst = client.sms().single_send(param);
			if (rst.getCode() == 0) {
				logger.info("向手机号"+phone+"发送找回登录密码短信成功！");
			} else {
				logger.info("向手机号"+phone+"发送找回登录密码短信失败，失败原因为："+rst.getMsg());
			}
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst.getCode();
	}
	
	/**
	 * 发送找支付密码验证码
	 * @param code
	 * @param phone
	 * @return
	 */
	public static Integer sendFindPayPwdCode(String code, String phone) {
		String text = "【学汇教育】正在进行找回密码操作，您的验证码是" + code ;
		Result<SmsSingleSend> rst = null;
		try {
			YunpianClient client = new YunpianClient(API_KEY).init();
			Map<String, String> param = client.newParam(2);
			param.put(YunpianClient.MOBILE, phone);
			param.put(YunpianClient.TEXT, text);
			rst = client.sms().single_send(param);
			if (rst.getCode() == 0) {
				logger.info("向手机号"+phone+"发送找回支付密码短信成功！");
			} else {
				logger.info("向手机号"+phone+"发送找回支付密码短信失败，失败原因为："+rst.getMsg());
			}
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst.getCode();
	}
	
	//发送入驻签约申请验证码
	public static Integer sendEnterApplyCode(String code, String phone) {
		String text = "【学汇教育】正在进行签约入驻操作，您的验证码是" + code;
		Result<SmsSingleSend> rst = null;
		try {
			YunpianClient client = new YunpianClient(API_KEY).init();
			Map<String, String> param = client.newParam(2);
			param.put(YunpianClient.MOBILE, phone);
			param.put(YunpianClient.TEXT, text);
			rst = client.sms().single_send(param);
			if (rst.getCode() == 0) {
				logger.info("向手机号"+phone+"发送入驻申请验证短信成功！");
			} else {
				logger.info("向手机号"+phone+"发送入驻申请验证短信失败，失败原因为："+rst.getMsg());
			}
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rst.getCode();
	}
	
	
}
