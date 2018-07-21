package com.jphy.lottery.testcase.API.synBet;

import com.jphy.lottery.APIHelper.BetSynHelper;
import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description K3投注接口测试
 */
public class Bet_Syn_003_K3_Test {
    public static Logger logger = Logger.getLogger(Bet_Syn_003_K3_Test.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting(ITestContext context) throws Exception{
        String lotteryType = "9";
        String filePath = "./src/test/resources/data/K3BetDatas.xml";
        while (true){
            String number = JdbcUtil.query(String.format("SELECT number FROM basic_number WHERE LOTTERY_TYPE = %s AND CREATE_TIME < SYSDATE() AND MODIFY_TIME > SYSDATE()", lotteryType),"number");
            BetSynHelper betSynHelper = new BetSynHelper(context, filePath, lotteryType,number);
            //投注
            if(betSynHelper.getCanbet()){
                betSynHelper.betLottery();
                //break;
            }else {
                logger.info("期号:"+number+"，已投注！");
                if (lotteryType.equals("8") || lotteryType.equals("9")){
                    sleep(180000);
                }else if (lotteryType.equals("10")){
                    sleep(10000);
                }
                continue;
            }
        }
    }
}
