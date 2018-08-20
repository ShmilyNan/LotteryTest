package com.jphy.lottery.plugins.father;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;

public class BetOrderFather {
    public static CloseableHttpAsyncClient httpClient = null;

    public static void initHttpClient() {
        try {
            //创建连接池
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(120 * 1000)
                    .setSocketTimeout(300 * 1000)
                    .setConnectionRequestTimeout(120 * 1000)
                    .build();
            IOReactorConfig ioReactorConfig = IOReactorConfig.custom().
                    setIoThreadCount(Runtime.getRuntime().availableProcessors())
                    .setSoKeepAlive(true)
                    .build();
            ConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(ioReactorConfig);
            PoolingNHttpClientConnectionManager mngr = new PoolingNHttpClientConnectionManager(ioreactor);
            mngr.setMaxTotal(100);
            mngr.setDefaultMaxPerRoute(10);
            httpClient = HttpAsyncClients.custom().
                    setConnectionManager(mngr)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
