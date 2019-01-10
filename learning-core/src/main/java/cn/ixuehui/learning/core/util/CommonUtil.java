package cn.ixuehui.learning.core.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 * @author szq
 * Date: 2019年1月7日 上午10:16:42
 */
public class CommonUtil {

	/**
	 * 密码加密
	 */
	public static String encryptPassword(String passwordStr){
		return DigestUtils.md5Hex(passwordStr);
	}
	
	/**
	 * 密码对比
	 */
	public static boolean passwordsMatch(String passwordStr, String encrypted){
		if(!StringUtils.isBlank(passwordStr)&&!StringUtils.isBlank(encrypted)){
			return encryptPassword(passwordStr).equalsIgnoreCase(encrypted);
		}
		return false;
	}
	
	/**
	 * 处理手机号码，隐藏中间四位数字
	 */
	public static String hideMsisdn(String msisdn){
		if(!StringUtils.isBlank(msisdn)&&ValidateUtil.Mobile(msisdn)){
			return msisdn.substring(0,3)+"****"+msisdn.substring(7);
		}
		return null;
	}
	
	 /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    /**
	 * 生成指定位数的随机数字
	 */
	public static String randomNum(int len){
		StringBuffer random = new StringBuffer(len);
		for(int i = 0; i < len; i++){
			random.append((int)(Math.random() * 10));
		}
		return random.toString();
	}
    
    /**
	 * 生成4位随机数字
	 */
	public static String randomNum(){
		return randomNum(4);
	}
	
	/**
	 * 获取UUID
	 */
	public static String getUUID(){
		String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
	
	/**
	 * 根据用户id生成logintoken
	 * @param id
	 * @return
	 */
	public static String loginToken(int id) {
		return getUUID().concat(InviteCodeUtil.toSerialCode(id));
	}
	
	public static void main(String[] args) {
		System.out.println(loginToken(0));
	}
}
