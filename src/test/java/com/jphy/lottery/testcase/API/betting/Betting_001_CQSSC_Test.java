package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Betting_001_CQSSC_Test {

    @Test(invocationCount = 50)
    public void orderBetting(ITestContext context) {
        String filePath = "./src/test/resources/res/CQSSCBetDatas.xml";
        BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "0");
        //投注
        betAPIHelper.betLottery();
    }
}
