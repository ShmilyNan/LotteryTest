package com.jphy.lottery.testcase.API.synBet;

import com.jphy.lottery.APIHelper.BetSynHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description PK10投注接口测试
 */
public class Bet_Syn_004_PK10_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_004_PK10_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception {
        int lotteryType = 1;
        String filePath = "./src/test/resources/data/PK10BetDatas.xml";
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        int number = numbers.size() / 64;
        BetSynHelper betSynHelper = new BetSynHelper(context, filePath, String.valueOf(lotteryType));
        for (int i = number * 7; i < number * 8; i++) {
            betSynHelper.betLottery(numbers.get(i));
        }
    }
}
