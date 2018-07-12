package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Betting_001_AMFFC_Test {
    public static Logger logger = Logger.getLogger(Betting_001_AMFFC_Test.class.getName());

    private CloseableHttpAsyncClient httpClient = null;

    private void initHttpClient() {
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
    public void orderBetting(ITestContext context) throws Exception {
        String filePath = "./src/test/resources/data/SSCBetDatas.xml";

        initHttpClient();

        int lotteryType = 6, count = 10000;
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType, count);
        for (int i = 0; i < numbers.size(); i++) {
            BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, String.valueOf(lotteryType), numbers.get(i));
            betAPIHelper.betLottery(httpClient);

            //Thread.sleep(5 * 1000);
        }
    }
}
