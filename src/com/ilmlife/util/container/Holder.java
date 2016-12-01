package com.ilmlife.util.container;
/**
 * 对象持有者
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2016年11月8日 下午20:28:07
 */
public class Holder<T> {
	private T holding;
	public Holder() {
	}
	public Holder( T value) {
		holding = value;
	}
	
	public T getValue() {
		return holding;
	}
	public void setValue(T holding) {
		this.holding = holding;
	}
}