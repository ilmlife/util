package com.ilmlife.util.data;

import java.util.Random;

/**
 * 随机数处理
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2015年7月22日 下午9:50:36
 */
public class RandomDataTool {
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
	
}
