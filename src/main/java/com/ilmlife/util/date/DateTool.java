package com.ilmlife.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 创建时间：2015年7月14日 下午9:06:27
 */
public class DateTool {
	/** 默认时间格式 **/
	public static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
	
	/**
	 * 获取系统当前的秒数
	 * @return 当前时间的秒数
	 */
	public static int getSystemSecond(){
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	
	/**
	 * 获取系统当前日期
	 * @return 模式为<b>DEFAULT_DATE_PATTERN</b>的日期
	 */
	public static String getCurTime() {
		return getCurTime(DEFAULT_DATE_PATTERN);
	}
	
	/**
	 * 获取系统当前时间
	 * @param pattern 当前时间格式化的模
	 * @return 模式为<b>pattern</b>当前时间
	 */
	public static String getCurTime(String pattern) {
		return formatTime(System.currentTimeMillis(), pattern);
	}
	
	/**
	 * 将毫秒转化为指定模式的字符串
	 * @param millis 要转化的毫秒数
	 * @param pattern 指定的模式
	 * @return
	 */
	public static String formatTime(long millis, String pattern) {
		return new SimpleDateFormat(pattern).format(new Date(millis));
	}
	
	
	/** 
	 * 秒数转化为DHMS格式描述
	 * @param seconds 要转化的描述
	 * @return
	 */
	public static String secTransTimeDesc(long seconds) {
		StringBuilder desc = new StringBuilder();
		
		long day = secTransDay(seconds, DATE_TYPE.DAY, false);
		seconds = seconds % DATE_TYPE.DAY.getSec();
		long hour = secTransDay(seconds, DATE_TYPE.HOUR, false);
		seconds = seconds % DATE_TYPE.HOUR.getSec();
		long min = secTransDay(seconds, DATE_TYPE.MIN, false);
		seconds = seconds % DATE_TYPE.MIN.getSec();
		
		desc.append(day > 0 ? day + "天" : "");
		desc.append(hour > 0 ? hour + "小时" : "");
		desc.append(min > 0 ? min + "分钟" : "");
		desc.append(seconds > 0 ? seconds + "秒" : "");
		
		return desc.toString();
	}
	
	/**
	 * 描述转化为type对应类型的值
	 * @param seconds 要转换的描述
	 * @param type 要转换的类型
	 * @param ceil 是否小数进位
	 * @return
	 */
	public static long secTransDay(long seconds, DATE_TYPE type, boolean ceil) {
		int sec = type.getSec();
		long num = seconds / sec;
		if(ceil) {
			if(seconds % sec > 0) {
				num += 1;
			}
		}
		return num;
	}
	
}
