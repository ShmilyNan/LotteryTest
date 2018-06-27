package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

/**
 * @author Lance
 * @Description PK10投注接口测试
 * */
public class Betting_009_PK10_Test {
	public static Logger logger = Logger.getLogger(Betting_009_PK10_Test.class.getName());

	@Test(invocationCount = 50)
	public void orderBetting(ITestContext context) throws Exception{
		String filePath = "./src/test/resources/res/PK10BetDatas.xml";
		BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "1");
		//投注
		if(betAPIHelper.getCanbet()){
			betAPIHelper.betLottery();
		}else {
			logger.info("当前期已投注！");
            sleep(330000);
        }
	}
}
