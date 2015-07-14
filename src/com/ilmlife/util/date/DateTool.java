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
	/** 1小时的秒数 **/
	public static int HOUR_SECOND = 3600;
	/** 1天的秒数 **/
	public static int ONEDAY_SECOND = 86400;
	
	
	
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
		return new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(new Date());
	}
	
	/**
	 * 获取系统当前时间
	 * @param pattern 当前时间格式化的模
	 * @return 模式为<b>pattern</b>当前时间
	 */
	public static String getCurTime(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
}
