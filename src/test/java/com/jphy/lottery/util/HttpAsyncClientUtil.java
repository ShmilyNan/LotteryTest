package com.jphy.lottery.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

public class CloseableHttpAsyncClientTest {

    private CloseableHttpAsyncClient httpClient = null;

    private void initHttpClient(){
        try{
            //创建连接池
            DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                    setConnectTimeout(10000).
                    setIoThreadCount(Runtime.getRuntime().availableProcessors()).
                    setSoTimeout(10000).
                    build());
            PoolingNHttpClientConnectionManager mngr = new PoolingNHttpClientConnectionManager(ioreactor);
            mngr.setMaxTotal(100);
            httpClient = HttpAsyncClientBuilder.create().setConnectionManager(mngr).build();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发起请求
     */
    private void doPost(String url){
        initHttpClient();
        try{
            //String url = "http://www.baidu.com";
            HttpPost request = new HttpPost(url);
            httpClient.start();
            httpClient.execute(request, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    System.out.println("请求成功");
                }

                public void failed(final Exception e) {
                    System.out.println("请求失败");
                }

                public void cancelled() {
                    System.out.println("请求取消");
                }
            });
        }catch (Exception e){
            System.out.println("请求异常");
        }
    }
}