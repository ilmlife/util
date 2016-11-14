package com.ilmlife.util;

/**
 * 调试工具类
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2016年11月8日 下午22:58:12
 */
public class DebugUtil {
	
	/**
	 * 获取当前线程栈信息
	 * @param debugFlag debug标记,用于定位
	 * @return
	 */
	public static String getCurrentThreadStack(String debugFlag) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append("Thread stack trace:[" + debugFlag + "]\n");
		if (stack != null && stack.length > 0) {
			for (int i = 0; i < stack.length; i++) {
				if (i == 0 || i == 1) {// 函数本身信息不需要打印
					continue;
				}
				StackTraceElement trace = stack[i];
				sb.append(trace.getClassName() + "." + trace.getMethodName() + "(" + trace.getFileName() + ":" + trace.getLineNumber() + ")\n");
			}
		}
		return sb.toString();
	}
	
	
}