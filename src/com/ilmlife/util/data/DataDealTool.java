package com.ilmlife.util.data;

import com.ilmlife.util.constant.ConstantSet;

/**
 * 数据处理工具
 * 
 * @author silly E-Mail：silly@intoms.cn
 * @version 1.0 创建时间：2016年10月28日 上午10:30:29
 */
public final class DataDealTool {
	
	/**
	 * 数字转换
	 * @param value 要转换的数据
	 * @return XXX亿XXX万格式
	 */
	public static String formatNumber(long value) {
		String vStr = String.valueOf(value);
		if(value <= 0) {
			return vStr;
		}
		
		String temp = "";
		long result[] = new long[3];
		for (int i = vStr.length() - 1,pos = 1,rIndex = 0; i >= 0; pos++, i--) {
			temp = vStr.charAt(i) + temp;
			if(pos % 4 == 0) {
				result[rIndex ++] = Integer.parseInt(temp);
				temp = "";
			}else if(i == 0) {
				result[rIndex ++] = Integer.parseInt(temp);
			}
		}
		
		String numStr = "";
		for (int i = result.length - 1, fPos = 0; i >= 0; fPos ++, i--) {
			numStr += result[i] + ConstantSet.NUMBER_FORMAT_STR[fPos];
		}
		
		return numStr;
	}
}