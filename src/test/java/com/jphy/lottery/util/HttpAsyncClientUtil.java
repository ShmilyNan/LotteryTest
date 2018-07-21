package com.jphy.lottery.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class HttpAsyncClientUtil {
    public static Logger logger = Logger.getLogger(HttpAsyncClientUtil.class.getName());
    private static String resultOfBet;
    public CloseableHttpAsyncClient httpClient = null;
    public CountDownLatch latch = new CountDownLatch(92);

    public void initHttpClient() {
        try {
            //创建连接池
            DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(IOReactorConfig.custom().
                    setConnectTimeout(10000).
                    setIoThreadCount(Runtime.getRuntime().availableProcessors()).
                    setSoTimeout(10000).
                    build());
            PoolingNHttpClientConnectionManager mngr = new PoolingNHttpClientConnectionManager(ioreactor);
            mngr.setMaxTotal(100);
            httpClient = HttpAsyncClientBuilder.create().setConnectionManager(mngr).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发起请求
     */
    public void doPost(String url, List<NameValuePair> params, final String lotteryType, final String number) {
        try {
            initHttpClient();
            HttpPost request = new HttpPost(url);
            try {
                request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                request.setHeader("Charset", "utf-8");
                request.setHeader("clientCode", "0");
                request.setHeader("terminal", "Android");
                request.setHeader("platformCode", "001");
                request.setHeader("deviceId", "867397038391761");
                request.setHeader("versionCode", "100");
            } catch (Exception e) {

            }
            httpClient.start();
            httpClient.execute(request, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    latch.countDown();
                    try {
                        resultOfBet = EntityUtils.toString(response.getEntity(), "utf-8");
                        if (!getBetInfo(resultOfBet).equals("投注成功")) {
                            logger.warn(String.format("%s第%s期请求不成功：%s", new Object[]{lotteryType, number, resultOfBet}));
                        }
                    } catch (IOException e) {
                        logger.warn(String.format("%s第%s期请求异常", new Object[]{lotteryType, number}), e);
                    }
                }

                public void failed(final Exception e) {
                    latch.countDown();
                    logger.warn(String.format("%s第%s期请求失败", new Object[]{lotteryType, number,}), e);
                }

                public void cancelled() {
                    latch.countDown();
                    logger.warn(String.format("%s第%s期投注取消", new Object[]{lotteryType, number}));
                }

            });
        } catch (Exception e) {
            logger.warn(String.format("%s第%s期请求异常", new Object[]{lotteryType, number}), e);
        }
    }

    private static String getBetInfo(String resultOfBet) {
        JSONObject json = JSONObject.parseObject(resultOfBet);
        return json.getString("msg");
    }
}