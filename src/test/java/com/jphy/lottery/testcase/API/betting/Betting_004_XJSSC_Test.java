package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
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

    @Test(invocationCount = 6)
    public void orderBetting(ITestContext context) throws Exception{
        String filePath = "./src/test/resources/data/SSCBetDatas.xml";
        while (true){
            String number = JdbcUtil.query(String.format("SELECT number FROM basic_number WHERE LOTTERY_TYPE = %d AND CREATE_TIME < NOW() AND MODIFY_TIME > NOW()", 4),"number");
            BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "4",number);
            //投注
            if(betAPIHelper.getCanbet()){
                betAPIHelper.betLottery(null);
                break;
            }else {
                logger.info("期号:"+number+"，已投注！");
                sleep(180000);
                continue;
            }
        }
    }
}
