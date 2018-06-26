package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description 11选5投注接口测试
 */
public class Betting_005_5Of11_Test {
    public static Logger logger = Logger.getLogger(Betting_005_5Of11_Test.class.getName());

    @Test(invocationCount = 5)
    public void orderBetting(ITestContext context) throws Exception{
        String filePathXml = "./src/test/resources/res/5Of11BetDatas.xml";
        BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePathXml, "11");
        //投注
        if(betAPIHelper.getCanbet()){
            betAPIHelper.betLottery();
        }else {
            logger.info("当前期已投注！");
            sleep(610000);

        }
    }
}