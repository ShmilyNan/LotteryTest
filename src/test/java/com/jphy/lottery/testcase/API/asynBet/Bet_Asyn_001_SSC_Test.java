package com.jphy.lottery.testcase.API.asynBet;

import com.jphy.lottery.APIHelper.BetAsynHelper;
import com.jphy.lottery.APIHelper.BetSynHelper;
import com.jphy.lottery.util.HttpAsyncClientUtil;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Bet_Asyn_001_SSC_Test {
    public static Logger logger = Logger.getLogger(Bet_Asyn_001_SSC_Test.class.getName());
    public CloseableHttpAsyncClient httpClient = null;

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

    @Test(invocationCount = 1)
    public void orderBetting(final ITestContext context) throws Exception {
        final String filePath = "./src/test/resources/data/SSCBetDatas.xml";
        final int lotteryType = 4;
        initHttpClient();
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        BetAsynHelper betAsynHelper = new BetAsynHelper(context, filePath, String.valueOf(lotteryType));
        for (int j = 0; j < numbers.size(); j++) {
            betAsynHelper.betLottery(httpClient, numbers.get(j));
        }
    }
}
