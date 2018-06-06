package com.lottery.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class URLConnection {    
	static String Info = "";
    public static HttpURLConnection getConnection(String url){
        HttpURLConnection connection = null;
        try {
            // 打开和URL之间的连接
            URL postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            /**
             *  设置通用的请求属性
             */
//          设置允许写出数据,默认是不允许 false
            connection.setDoOutput(true);
//          当前的连接可以从服务器读取内容, 默认是true
            connection.setDoInput(true);
//          设置请求类型
            connection.setRequestMethod("POST");
//         	POST不能使用缓存
            connection.setUseCaches(false);
//          是否自动重定向
            connection.setInstanceFollowRedirects(true);
//          设定传送的内容是可序列化的java对象，否则在传送可序列化的java对象时可能抛出EOFException
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");
//          获取向服务器写出的数据流
            OutputStream outputStream = connection.getOutputStream();
//          参数是键值对，不以“？”开头
            outputStream.write(Info.getBytes());
            outputStream.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return connection;
    }
}
