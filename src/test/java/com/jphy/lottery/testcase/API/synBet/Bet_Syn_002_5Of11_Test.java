package com.jphy.lottery.testcase.API.synBet;

import com.jphy.lottery.APIHelper.BetSynHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description 11选5投注接口测试
 */
public class Bet_Syn_002_5Of11_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_002_5Of11_Test.class.getName());
    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception{
        String lotteryType = "11";
        String filePathXml = "./src/test/resources/data/5Of11BetDatas.xml";
        while (true){
            String number = JdbcUtil.query(String.format("SELECT number FROM basic_number WHERE LOTTERY_TYPE = %s AND CREATE_TIME < SYSDATE() AND MODIFY_TIME > SYSDATE()", lotteryType),"number");
            BetSynHelper betSynHelper = new BetSynHelper(context, filePathXml, lotteryType,number);
            //投注
            if(betSynHelper.getCanbet()){
                betSynHelper.betLottery();
                //break;
            }else {
                logger.info("期号:"+number+"，已投注！");
                sleep(180000);
                continue;
            }
        }
    }
}