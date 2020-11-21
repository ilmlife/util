package com.ilmlife.util.text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符处理工具
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 创建时间：2015年7月14日 下午9:06:59
 */
public class TextTool {
	/**
	 * 验证content是否满足regex表达式
	 * 
	 * @param regex 正则表达式
	 * @param content 要验证的内容
	 * @return true 验证通过<br/>false 验证失败
	 */
	public static boolean valid(String regex,String content){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		return m.matches();
	}
	
	/**
	 * base64处理
	 * @param value 需要处理的字符串
	 * @param prefix 处理结果的前缀
	 * @param code 编码格式
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	public static String base64Encoder(String value,String prefix,String code) throws UnsupportedEncodingException {
		String base64Value = Base64.getEncoder().encodeToString(value.getBytes(code));
		base64Value = prefix + base64Value;
		return base64Value;
	}
	/**
	 * base64解码处理
	 * @param value 需要处理的字符串
	 * @param prefix 处理结果的前缀
	 * @param code 编码格式
	 * @return 
	 * @throws IOException 
	 */
	public static String base64Decoder(String value,String prefix,String code) throws IOException {
		byte[] base64ValueBytes = Base64.getDecoder().decode(value.substring(prefix.length()));;
    	return new String(base64ValueBytes, code);         
	}
}
