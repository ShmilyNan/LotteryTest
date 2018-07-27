package com.jphy.lottery.testcase.API.asynBet;

import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Bet_Asyn_002_SSC_Test {
    public static Logger logger = Logger.getLogger(Bet_Asyn_002_SSC_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception {
        //final String filePath = "./src/test/resources/data/SSCBetDatas.xml";
        final int lotteryType = 4;
        int orders = 92;
        //JdbcUtil.insertNumbers(lotteryType);
        while (true) {
            List<String> numbers = JdbcUtil.queryNumbersToUpdateResult(lotteryType, orders);
            if (numbers.size() == 0) {
                Thread.sleep(60000);
            } else {
                for (int i = 0; i < numbers.size(); i++) {
                    JdbcUtil.updateResult(lotteryType, numbers.get(i));
                }
                //JdbcUtil.insertNumbers(lotteryType);
            }
        }
    }
}
