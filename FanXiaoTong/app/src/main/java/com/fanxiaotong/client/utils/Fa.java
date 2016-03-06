package com.fanxiaotong.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class Fa {
	public Fa() {
	}
	public static <T> T getSignal(String jsonString,Class<T> cls)
	{
		T t = null;
		t = JSON.parseObject(jsonString, cls);
		return t;
	}
	public static <T> List<T> getList(String jsonString,Class<T> cls)
	{
		List<T> list = new ArrayList<T>();
		list = JSON.parseArray(jsonString, cls);
		return list;
	}
}