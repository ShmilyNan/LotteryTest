package com.jphy.lottery.testcase.API.asynBet;

import com.jphy.lottery.APIHelper.BetAsynHelper;
import com.jphy.lottery.plugins.father.BetOrderFather;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import java.util.List;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Bet_Asyn_001_SSC_Test {
    public static Logger logger = Logger.getLogger(Bet_Asyn_001_SSC_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(final ITestContext context) {
        final String filePath = "./src/test/resources/data/SSCBetDatas.xml";
        final int lotteryType = 4;
        BetOrderFather.initHttpClient();
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        BetAsynHelper betAsynHelper = new BetAsynHelper(context, filePath, String.valueOf(lotteryType));
        for (int j = 0; j < numbers.size(); j++) {
            betAsynHelper.betLottery(BetOrderFather.httpClient, numbers.get(j));
        }
    }
}
