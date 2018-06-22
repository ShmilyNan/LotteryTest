package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import com.jphy.lottery.util.JdbcUtil;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Lance
 * @Description 11选5投注接口测试
 */
public class Betting_002_5Of11_Test {

    @Test(invocationCount = 1000)
    public void orderBetting(ITestContext context) {
        String filePathXml = "./src/test/resources/res/5Of11BetDatas.xml";
        long start = System.currentTimeMillis();
        while (true) {
            BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePathXml, "11");
            //投注
            betAPIHelper.betLottery();
            if ((System.currentTimeMillis() - start) > 3600000 * 10) {
                break;
            }
        }
    }
}