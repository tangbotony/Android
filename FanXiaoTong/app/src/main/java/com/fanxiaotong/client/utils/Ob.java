package com.fanxiaotong.client.utils;

import com.alibaba.fastjson.JSON;

public class Ob {
	public static String createJsonString(Object value)
	{
		return  JSON.toJSONString(value);
	}
}
