package com.ilmlife.util.date;
/**
 * 时间类型
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2015年7月14日 下午9:50:36
 */
public enum DATE_TYPE {
	DAY(86400),
	HOUR(3600),
	MIN(60),
	SEC(1);
	
	private int sec;
	
	DATE_TYPE(int sec) {
		this.sec = sec;
	}
	public int getSec() {
		return sec;
	}
	
}
