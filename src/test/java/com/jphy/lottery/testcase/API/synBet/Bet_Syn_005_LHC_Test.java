package com.jphy.lottery.testcase.API.synBet;

import com.jphy.lottery.APIHelper.BetSynHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Lance
 * @Description PK10投注接口测试
 */
public class Bet_Syn_005_LHC_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_005_LHC_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) {
        int lotteryType = 14;
        String filePath = "./src/test/resources/data/LHCBetDatas.xml";
        List<String> numbers = JdbcUtil.queryNumbers(lotteryType);
        BetSynHelper betSynHelper = new BetSynHelper(context, filePath, String.valueOf(lotteryType));
        for (int i = 0; i < 1; i++) {
            betSynHelper.betLottery(numbers.get(i));
        }
    }
}
