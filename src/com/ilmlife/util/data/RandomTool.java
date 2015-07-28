package com.ilmlife.util.data;

import java.util.Random;

import com.ilmlife.util.constant.ConstantSet;
import com.ilmlife.util.exception.ErrorParamsException;
import com.ilmlife.util.text.TextTool;

/**
 * 随机数据处理
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2015年7月22日 下午9:50:36
 */
public class RandomTool {
	/**
	 * 计算data随机按向下middle向上middle浮动后得到的数据<middle为正整数>
	 * @param data 需要计算的数据
	 * @param middle 上下浮动一样<25,0.75-1.25>
	 * @return 浮动后的数值
	 */
	public static int getFloatData(int data,int middle){
		Random random = new Random();
		return getFloatData(data, middle, random);
	}
	/**
	 * 计算data随机按向下middle向上middle浮动后得到的数据<middle为正整数>
	 * @param data 需要计算的数据
	 * @param middle 上下浮动一样<25,0.75-1.25>
	 * @param random 
	 * @return 浮动后的数值
	 */
	public static int getFloatData(int data,int middle,Random random){
		int randomSeed = middle + middle;
		int num = random.nextInt(randomSeed);
		return (int) ((1+((double)(num - middle))/100) * data);
	}
	
	/**
	 * 随机范围内一个值
	 * @param start 范围的开始
	 * @param end 范围的结束(不包含该数)
	 * @return 随机的值
	 */
	public static int getRandomFromRange(int start,int end){
		return getRandomFromRange(start, end, false);
	}
	
	/**
	 * 随机范围内一个值
	 * @param start 范围的开始
	 * @param end 范围的结束(不包含该数)
	 * @param random
	 * @return 随机的值
	 */
	public static int getRandomFromRange(int start,int end,Random random){
		return getRandomFromRange(start, end, false, random);
	}
	
	/**
	 * 随机范围内一个值
	 * @param start 范围的开始
	 * @param end 范围的结束
	 * @param closeInterval true--包含边界值   false--不包含边界值
	 * @return 随机的值
	 */
	public static int getRandomFromRange(int start,int end,boolean closeInterval){
		Random random = new Random();
		return getRandomFromRange(start, end, closeInterval, random);
	}
	/**
	 * 随机范围内一个值
	 * @param start 范围的开始
	 * @param end 范围的结束
	 * @param closeInterval true--包含边界值   false--不包含边界值
	 * @param random
	 * 
	 * @return 随机的值
	 */
	public static int getRandomFromRange(int start,int end,boolean closeInterval, Random random){
		int randomSeed = end - start;
		if (closeInterval){
			randomSeed += 1;
		}
		int num = random.nextInt(randomSeed);
		return start + num;
	}
	
	
	/**
	 * 获取随机字符串(只支持数字、大小写字母、常用符号)
	 * 
	 * @param length 随机字符串的长度
	 * @param upLowSeed 生成随机数的规则,其值为一个字符串,字符串中字符含义为：[1:数字;2:大写字母;3:小写字母;4:标点符号]<br/>
	 * 
	 * @return 长度为length的随机数
	 * @throws ErrorParamsException 参数错误异常
	 */
	public static String genRandomStr(int length,String upLowSeed) throws ErrorParamsException{
		if(upLowSeed == null || !TextTool.valid("[1-4]{1,4}", upLowSeed)) {
			throw new ErrorParamsException(upLowSeed);
		}
		
		StringBuilder sb = new StringBuilder(length);
		
		int index = 0;
		Random random = new Random();
		while(index < length){
			char seed = upLowSeed.charAt(random.nextInt(upLowSeed.length()));
			switch (seed) {
				case '1': {
					sb.append(ConstantSet.BASE_NUMBERS[random.nextInt(ConstantSet.BASE_NUMBERS.length)]); 
				}; break;
				case '2':{
					sb.append(ConstantSet.UPPERCASE[random.nextInt(ConstantSet.UPPERCASE.length)]); 
				}; break;
				case '3':{
					sb.append(ConstantSet.LOWERCASE[random.nextInt(ConstantSet.LOWERCASE.length)]); 
				}; break;
				case '4':{
					sb.append(ConstantSet.PUNCTUATION[random.nextInt(ConstantSet.PUNCTUATION.length)]); 
				}; break;
				default:{
					sb.append(ConstantSet.BASE_NUMBERS[random.nextInt(ConstantSet.BASE_NUMBERS.length)]); 
				}; break;
			}
			index++;
		}
		return sb.toString();
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(genRandomStr(20, "1234"));
	}
}
