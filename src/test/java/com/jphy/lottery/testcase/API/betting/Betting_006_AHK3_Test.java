package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description K3投注接口测试
 */
public class Betting_006_AHK3_Test {
    public static Logger logger = Logger.getLogger(Betting_006_AHK3_Test.class.getName());
    public static String number;
    public static String numberId;

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception {
        String filePath = "./src/test/resources/data/AHK3BetDatas.xml";
        number = JdbcUtil.query(String.format("SELECT number FROM basic_number WHERE LOTTERY_TYPE = %d AND CREATE_TIME < SYSDATE() AND MODIFY_TIME > SYSDATE()", 9),"number");
        numberId = JdbcUtil.query(String.format("SELECT ID FROM basic_number WHERE LOTTERY_TYPE = %d AND NUMBER = %s", 9,number),"id");
        for(int i = 0;i<20;i++) {
            BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "9", number);
            //投注
            if (betAPIHelper.getCanbet()) {
                betAPIHelper.betLottery(null);
            } else {
                number = JdbcUtil.query(String.format("select * from basic_number r where r.LOTTERY_TYPE = %d and r.id > %s order by r.ID asc LIMIT 1",9,numberId),"number");
                numberId = JdbcUtil.query(String.format("SELECT ID FROM basic_number WHERE LOTTERY_TYPE = %d AND NUMBER = %s", 9,number),"id");
                sleep(3000);
            }
        }
    }
}
