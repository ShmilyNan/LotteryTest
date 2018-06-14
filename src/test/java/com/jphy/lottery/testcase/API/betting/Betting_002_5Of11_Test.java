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

    @Test
    public void orderBetting(ITestContext context) {
        String filePathXml = "./src/test/resources/res/5Of11BetDatas.xml";
        BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePathXml, "11", "0", "02,05,07,03,08");
        //投注
        betAPIHelper.betLottery();
        //指定开奖
        betAPIHelper.openLottery();
    }
}