package com.ilmlife.util.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 具有大小限制的集合<br/>
 * <br/>
 * 特征：<br/>
 * 1.超出上限时自动删除最早插入的节点<br/>
 * 2.可以根据索引访问<br/>
 * 3.可以根据ID以O(1)的时间访问<br/>
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 1.0 创建时间：2016年11月8日 下午23:06:30
 */
public class SizeLimitedHolder<T extends HasID> implements Serializable {
	private static final long serialVersionUID = -3560947667606179545L;

	/** 默认限制大小 **/
	static final int DEFAULT_INITIAL_LIMITSIZE = 20;

	private List<T> holder; 			// 存储元素(常修改,使用链表结构)
	private Map<String, T> keyRecord; 	// 元素key与元素的对应关系
	private int limitedSize; 			// 限制大小

	public SizeLimitedHolder() {
		this.holder = new LinkedList<T>();
		this.keyRecord = new HashMap<String, T>(DEFAULT_INITIAL_LIMITSIZE);
		this.limitedSize = DEFAULT_INITIAL_LIMITSIZE;
	}

	public SizeLimitedHolder(int limitedSize) {
		this.holder = new LinkedList<T>();
		this.keyRecord = new HashMap<String, T>(limitedSize);
		this.limitedSize = limitedSize;
	}

	/**
	 * 添加一个元素,如果元素存在不替换直接返回
	 * 
	 * @param node 要添加的元素
	 */
	public void add(T node) {
		if (keyRecord.containsKey(node.getID())) {
			return;
		}
		holder.add(node);
		keyRecord.put(node.getID(), node);
		if (limitedSize <= 0) {
			limitedSize = DEFAULT_INITIAL_LIMITSIZE;
		}
		if (holder.size() > limitedSize) {// 删除第一个元素
			keyRecord.remove(holder.get(0).getID());
			holder.remove(0);
		}
	}

	/**
	 * 根据索引移除数据
	 * 
	 * @param index 索引
	 * @return 移除结果
	 */
	public boolean remove(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
					+ this.size());
		}
		T node = this.holder.remove(index);
		if (node == null) {
			return false;
		}
		this.keyRecord.remove(node.getID());
		return true;
	}

	/**
	 * 根据节点ID移除数据
	 * 
	 * @param id 节点ID
	 * @return
	 */
	public boolean remove(String id) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).getID().equals(id)) {
				return this.remove(i);
			}
		}
		return false;
	}

	/**
	 * 添加一个元素,如果元素存在替换
	 * 
	 * @param node 要添加的元素
	 */
	public void addAndReplace(T node) {
		if (keyRecord.containsKey(node.getID())) {
			this.remove(node.getID());
		}
		this.add(node);
	}

	/**
	 * 根据索引获取节点
	 * 
	 * @param index 索引
	 * @return
	 */
	public T get(int index) {
		T node = holder.get(index);
		if (node != null && !keyRecord.containsKey(node.getID())) {
			holder.remove(index);
			return null;
		}
		return holder.get(index);
	}

	/**
	 * 根据ID访问
	 * 
	 * @param ID 节点ID
	 * @return
	 */
	public T get(String ID) {
		return keyRecord.get(ID);
	}

	/**
	 * 是否存在该ID的节点
	 * 
	 * @param ID 节点ID
	 * @return
	 */
	public boolean contains(String ID) {
		return keyRecord.containsKey(ID);
	}

	/**
	 * 当前容器大小
	 * 
	 * @return
	 */
	public int size() {
		return holder.size();
	}

	/**
	 * 插入顺序访问
	 * 
	 * @return
	 */
	public Iterator<T> iterator() {
		return holder.iterator();
	}

	/**
	 * 删除所有
	 */
	public void removeAll() {
		this.holder.clear();
		this.keyRecord.clear();
	}
}