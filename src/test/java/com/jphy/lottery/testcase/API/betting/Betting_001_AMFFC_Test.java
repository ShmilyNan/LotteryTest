package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class Betting_001_AMFFC_Test {
    public static Logger logger = Logger.getLogger(Betting_001_AMFFC_Test.class.getName());

    @Test(invocationCount = 60)
    public void orderBetting(ITestContext context) throws Exception {
        String filePath = "./src/test/resources/res/AMFFCBetDatas.xml";
        while (true) {
            String number = JdbcUtil.query(String.format("SELECT number FROM basic_number WHERE LOTTERY_TYPE = %d AND CREATE_TIME < NOW() AND MODIFY_TIME > NOW()", 6),"number");
            BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "6",number);
            //投注
            if (betAPIHelper.getCanbet()) {
                betAPIHelper.betLottery();
                break;
            } else {
                logger.info("当前期已投注！");
                sleep(5000);
                continue;
            }
        }
    }
}
