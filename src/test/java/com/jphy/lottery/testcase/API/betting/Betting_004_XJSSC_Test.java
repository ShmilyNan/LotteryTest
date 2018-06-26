package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Betting_004_XJSSC_Test {
    public static Logger logger = Logger.getLogger(Betting_004_XJSSC_Test.class.getName());

    @Test(invocationCount = 5)
    public void orderBetting(ITestContext context) throws Exception{
        String filePath = "./src/test/resources/res/XJSSCBetDatas.xml";
        BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "4");
        //投注
        if(betAPIHelper.getCanbet()){
            betAPIHelper.betLottery();
        }else {
            logger.info("当前期已投注！");
            sleep(610000);
        }
    }
}
