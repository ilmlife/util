package com.ilmlife.util.exception;
/**
 * 参数错误异常
 *
 * @author silly E-Mail：silly@intoms.cn
 * @version 1.0 创建时间：2015年7月28日 上午11:16:56
 */
public class ErrorParamsException extends Exception {
	private static final long serialVersionUID = -4933194633716556580L;
	
	public ErrorParamsException(String param) {
		super("params[" + param + "] mismatch");
	}
	
	public ErrorParamsException(String param, Throwable e) {
		super("params[" + param + "] mismatch", e);
	}
}
