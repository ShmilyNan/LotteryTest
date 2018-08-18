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
 * @Description 11选5投注接口测试
 */
public class Bet_Syn_002_5Of11_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_002_5Of11_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception {
        int lotteryType = 11;
        String filePath = "./src/test/resources/data/5Of11BetDatas.xml";
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        int number = numbers.size() / 64;
        BetSynHelper betSynHelper = new BetSynHelper(context, filePath, String.valueOf(lotteryType));
        for (int i = number * 3; i < number * 4; i++) {
            betSynHelper.betLottery(numbers.get(i));
        }
    }
}