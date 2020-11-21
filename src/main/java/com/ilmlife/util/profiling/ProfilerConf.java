package com.ilmlife.util.profiling;
/**
 * 分析配置信息(还没想好这个类放哪)
 *
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2016年11月15日 上午21:16:56
 */
public class ProfilerConf {
	private static ProfilerConf instance = new ProfilerConf();
	private ProfilerConf(){}
	public static ProfilerConf getInstance() {
		return instance;
	}
	
	private boolean profileActivate;	// 是否开放分析
	private int slowTimeMs;				// 分析需要达到的时间(秒)
	
	public int getSlowTimeMs() {
		return slowTimeMs;
	}
	public boolean isProfileActivate() {
		return profileActivate;
	}
	
}