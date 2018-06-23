package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
import com.jphy.lottery.util.SeleniumUtil;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Betting_001_SSC_Test {

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) {
        String filePath = "./src/test/resources/res/SSCBetDatas.xml";
        //long start = System.currentTimeMillis();
        BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "0");
        //投注
        betAPIHelper.betLottery();

        /*
        while (true) {
            BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "0");
            //投注
            betAPIHelper.betLottery();
            if ((System.currentTimeMillis() - start) > 3600000 * 10) {
                break;
            }
        }
        */
    }
}
