package com.jphy.lottery.testcase.API.synBet;

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

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Bet_Syn_001_SSC_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_001_SSC_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception {
        String filePath = "./src/test/resources/data/K3BetDatas.xml";
        String number = "";
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                for (int k = 1; k <= 6; k++) {
                    number = String.valueOf(i) + String.valueOf(j) + String.valueOf(k);
                    BetSynHelper betSynHelper = new BetSynHelper(context, filePath, "9", number);
                    //投注
                    if (betSynHelper.getCanbet()) {
                        betSynHelper.betLottery();
                    } else {
                        logger.info("期号:" + number + "，已投注！");
                        sleep(5000);
                    }
                }
            }
        }
    }
}
