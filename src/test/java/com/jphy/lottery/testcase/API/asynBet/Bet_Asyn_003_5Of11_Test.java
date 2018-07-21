package com.jphy.lottery.testcase.API.asynBet;

import com.jphy.lottery.APIHelper.BetAsynHelper;
import com.jphy.lottery.APIHelper.BetSynHelper;
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
 * @Description 11选5投注接口测试
 */
public class Bet_Asyn_003_5Of11_Test {
    public static Logger logger = Logger.getLogger(Bet_Asyn_003_5Of11_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception {
        String filePath = "./src/test/resources/data/5Of11BetDatas.xml";
        final int lotteryType = 11;

        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        for (int j = 19999; j < numbers.size(); j++) {
            if (j < 20000) {
                BetAsynHelper betAsynHelper = new BetAsynHelper(context, filePath, String.valueOf(lotteryType), numbers.get(j));
                betAsynHelper.betLottery();
            }
        }
    }
}