package com.jphy.lottery.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HttpUtils {
	private static final int CONNECTION_TIME_OUT = 5000;

	@SuppressWarnings({ "resource" })
	public static String doPost(String url, String param) {
		String body = null;
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			if (!StringUtils.isEmpty(param)) {
				String[] keyValues = StringUtils.split(param, "&");
				for (int i = 0; i < keyValues.length; i++) {
					formParams.add(new BasicNameValuePair(StringUtils.split(keyValues[i], "=")[0],
							StringUtils.split(keyValues[i], "=")[1]));
				}
			}

			// 请求超时
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIME_OUT);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECTION_TIME_OUT);

			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			post.setHeader("Charset", "utf-8");
			post.setHeader("clientCode", "0");
			post.setHeader("terminal", "Android");
			post.setHeader("platformCode", "002");
			post.setHeader("deviceId", "867397038391761");
			post.setHeader("versionCode", "100");
			post.setHeader("ip", "222.209.33.199");

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "utf-8");
			post.setEntity(formEntity);

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "utf-8");

			formEntity.consumeContent();
			client.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	@SuppressWarnings({ "resource" })
	public static String doGet(String url, String param) {
		String body = null;
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpParams myParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(myParams, CONNECTION_TIME_OUT);
			HttpConnectionParams.setSoTimeout(myParams, CONNECTION_TIME_OUT);

			HttpGet get = new HttpGet(url + "?" + param);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "utf-8");
			entity.consumeContent();
			client.getConnectionManager().shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

}
