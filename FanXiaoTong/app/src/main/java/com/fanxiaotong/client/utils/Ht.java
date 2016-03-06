package com.fanxiaotong.client.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import com.fanxiaotong.client.config.ConfigurationFiles;

/**
 * 使用方法： map.put("username", "admin"); map.put("password", "123"); String result
 * = SendHttpClientPost("http://localhost:8080/myhttp//servlet/LoginServlet", *
 * map, "utf-8"); LittleUtil.printlnLog("--result-->"+result);
 */
public class Ht {
	public static HashMap<String, String> CookieContiner = new HashMap<String, String>();

	public static String SendHttpClientPost(String path,
			Map<String, String> parm, String encode, Context context) {

		String url = Li.d(path);
		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		System.out.println("url:"+url);
		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		if (Li.isConn(context)) {
			Li.printlnLog("路径：" + url);
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			if (parm != null && !parm.isEmpty()) {
				for (Entry<String, String> entry : parm.entrySet()) {
					list.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
			}
			try {
				// 实现将参数分装到请求体中
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
						list, encode);
				// 使用post传递数据
				HttpPost httpPost = new HttpPost(url);
				addCookies(httpPost);
				// 将实体封装到Post中
				httpPost.setEntity(urlEncodedFormEntity);
				DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
				try {
					HttpResponse httpResponse = defaultHttpClient
							.execute(httpPost);
					SaveCookies(httpResponse);
					Cookie cookie = getCookieValue(defaultHttpClient,
							"phoneNumber");
					Li.printlnLog("CookieContiner:"
							+ CookieContiner.toString());
					BasicCookieStore cookieStore = (BasicCookieStore) defaultHttpClient
							.getCookieStore();
					List<Cookie> cookiesList = cookieStore.getCookies();
					Li.printlnLog("cookiesList--------------------num:"
							+ cookiesList.size());
					Li.printlnLog("cookieStore:" + cookie);
					Li.printlnLog("返回码："
							+ httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						Li.printlnLog("getStatusCode() == 200");
						String result = changeInputStream(httpResponse
								.getEntity().getContent(), encode);
						if (result.equals(ConfigurationFiles.OTHER_PLACE_LOGIN)) {
							return ConfigurationFiles.OTHER_PLACE_LOGIN;
						} else {
							return result;
						}
					} else {
						Li.printlnLog("返回码："
								+ httpResponse.getStatusLine().getStatusCode());
						return ConfigurationFiles.HTTP_ERROR;
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ConfigurationFiles.HTTP_ERROR;
	}

	private static String changeInputStream(InputStream inputStream,
			String encode) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		if (inputStream != null) {
			byte[] mydate = new byte[1024];
			int len = 0;
			try {
				while ((len = inputStream.read(mydate)) != -1) {
					byteArrayOutputStream.write(mydate, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String result = "";
		try {
			result = new String(byteArrayOutputStream.toByteArray(), encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static Cookie getCookieValue(DefaultHttpClient httpclient,
			String name) {
		Li.printlnLog("----*****----getCookieValue start----------");
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		Li.printlnLog("------cookies:" + cookies.size() + "--------");
		if (cookies.isEmpty()) {
			System.out
					.println("-----*****----EmptyCookie----getCookieValue end---------");
			return null;
		}
		for (int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			Li.printlnLog("-----cookie[" + i + "]:" + cookie.getName()
					+ "----------");
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 保存Cookie
	 * 
	 * @param resp
	 */
	public static void SaveCookies(HttpResponse httpResponse) {
		org.apache.http.Header[] headers = httpResponse
				.getHeaders("Set-Cookie");
		// getAllHeaders();//
		/*
		 * for (int i = 0; i < headers.length; i++) {
		 * LittleUtil.printlnLog(headers[i].toString()); }
		 */
		for (int i = 0; i < headers.length; i++) {
			String cookie = headers[i].getValue();
			String[] cookievalues = cookie.split(";");
			for (int j = 0; j < cookievalues.length; j++) {
				String[] keyPair = cookievalues[j].split("=");
				String key = keyPair[0].trim();
				Li.printlnLog("key:" + key);
				String value = keyPair.length > 1 ? keyPair[1].trim() : "";
				Li.printlnLog("value:" + value);
				CookieContiner.put(key, value);
			}
		}
	}

	/**
	 * 增加Cookie
	 * 
	 * @param request
	 */
	public static void addCookies(HttpPost request) {
		if (CookieContiner == null)
			return;
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iter = CookieContiner.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			sb.append(key);
			sb.append("=");
			sb.append(val);
			sb.append(";");
		}
		request.addHeader("cookie", sb.toString());
	}

}
