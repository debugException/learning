package cn.ixuehui.learning.core.util;

import java.math.BigDecimal;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 */
public class DoubleUtil {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的double类型加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的String类型加法运算。 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和的String类型
	 */
	public static String add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		double sum = b1.add(b2).doubleValue();
		return String.valueOf(sum);
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b1 = new BigDecimal(String.valueOf(v1));
		BigDecimal b2 = new BigDecimal(String.valueOf(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b = new BigDecimal(String.valueOf(v));
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 只舍不如 默认保留两位有效数字
	 * 2018年6月21日上午10:03:51
	 * @param obj 输入的数据
	 * @return
	 */
    public static double ExShe(double obj) {
    	return BigDecimal.valueOf(obj).setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
    }
}
