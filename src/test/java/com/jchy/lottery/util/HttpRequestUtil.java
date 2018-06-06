package com.jchy.lottery.util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author  Lance
 * @description 处理http请求 get和post
 * 
 * */
public class HttpRequestUtil {
	/**
	 * 发送HttpPost请求
	 * @param strURL:  服务地址
	 * @param jsonParams: json字符串,例如: "{\"username\":\"鄂F55555\",\"password\":\"123456\"}" ;其中属性名必须带双引号
	 * @return 成功:返回json字符串
	 */
	public static String post(String strURL, String jsonParams) {
		String result="";
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;   
			connection.setDoOutput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;  
			connection.setDoInput(true);
			//post请求不能使用缓存 故为false
			connection.setUseCaches(false);
			//setInstanceFollowRedirects是成员函数，仅作用于当前函数
			connection.setInstanceFollowRedirects(true);
			// 设定请求的方法为"POST"，默认是GET
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.setRequestProperty("clientCode", "003");
			connection.setRequestProperty("terminal", "PC");
			connection.setRequestProperty("platformCode", "123");
			connection.setRequestProperty("deviceId", "123");
			
			// 连接，从上述中url.openConnection()至此的配置必须要在connect之前完成
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(jsonParams);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "UTF-8"); // utf-8编码
				System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	

}
