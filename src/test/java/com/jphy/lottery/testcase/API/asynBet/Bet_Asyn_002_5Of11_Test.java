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
 * @Description 11选5投注接口测试
 */
public class Bet_Asyn_002_5Of11_Test {
    public static Logger logger = Logger.getLogger(Bet_Asyn_002_5Of11_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) {
        String filePath = "./src/test/resources/data/5Of11BetDatas.xml";
        final int lotteryType = 11;
        BetOrderFather.initHttpClient();
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        int number = numbers.size() / 7;
        BetAsynHelper betAsynHelper = new BetAsynHelper(context, filePath, String.valueOf(lotteryType));
        for (int j = 0; j < number * 1; j++) {
            betAsynHelper.betLottery(BetOrderFather.httpClient, numbers.get(j));
        }
    }
}