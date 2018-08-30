package com.jphy.lottery.testcase.API.synBet;

import com.jphy.lottery.APIHelper.BetSynHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;


/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Bet_Syn_001_SSC_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_001_SSC_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) {
        int lotteryType = 5;
        String filePath = "./src/test/resources/data/SSCBetDatas.xml";
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        BetSynHelper betSynHelper = new BetSynHelper(context, filePath, String.valueOf(lotteryType));
        for (int i = 0; i < 1; i++) {
            betSynHelper.betLottery(numbers.get(i));
        }
    }
}