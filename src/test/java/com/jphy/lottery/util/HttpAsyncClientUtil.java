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

public class HttpAsyncClientUtil {
    public static Logger logger = Logger.getLogger(HttpAsyncClientUtil.class.getName());
    private static String resultOfBet;

    /**
     * 发起请求
     */
    public void doPost(String url, List<NameValuePair> params, final String lotteryType, final String number, CloseableHttpAsyncClient httpClient) {
        try {
            //String url = "http://www.baidu.com";
            HttpPost request = new HttpPost(url);
            try {
                request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                request.setHeader("Charset", "utf-8");
                request.setHeader("clientCode", "0");
                request.setHeader("terminal", "Android");
                request.setHeader("platformCode", "001");
                request.setHeader("deviceId", "867397038391761");
                request.setHeader("versionCode", "1");
            } catch (Exception e) {

            }
            httpClient.start();
            httpClient.execute(request, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    try {
                        resultOfBet = EntityUtils.toString(response.getEntity(), "utf-8");
                        //System.out.println(getBetInfo(EntityUtils.toString(response.getEntity(),"utf-8")));
                        if (!getBetInfo(resultOfBet).equals("投注成功")) {
                            logger.warn(String.format("%s第%s期请求不成功：%s", new Object[]{lotteryType, number, resultOfBet}));
                        }
                    } catch (IOException e) {
                        logger.warn(String.format("%s第%s期请求异常", new Object[]{lotteryType, number}), e);
                    }
                }

                public void failed(final Exception e) {
                    logger.warn(String.format("%s第%s期请求失败", new Object[]{lotteryType, number,}), e);
                }

                public void cancelled() {
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