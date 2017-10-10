package com.kawakp.kp.application;

import io.realm.RealmObject;

/**
 * 创建人: penghui.li
 * 创建时间: 2017/10/9
 * 修改人:penghui.li
 * 修改时间:2017/10/9
 * 修改内容:
 *
 * 功能描述:
 */
public class User extends RealmObject {
	private String name;
	private int age;

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
