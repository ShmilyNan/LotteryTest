package com.jphy.lottery.plugins.father;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

public class BetOrderFather {
    public static CloseableHttpAsyncClient httpClient = null;

    public static void initHttpClient() {
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
}
