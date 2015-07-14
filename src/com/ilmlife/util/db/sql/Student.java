package com.ilmlife.util.db.sql;
/**
 * 测试类
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 创建时间：2015年7月14日 下午9:06:49
 */
public class Student extends Person {
	private String stuNo;

	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
}

class Person {
	private String ID;
	private String name;
	private int age;

	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
