package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description K3投注接口测试
 * */
public class Betting_003_AHK3_Test {

	@Test(invocationCount = 50)
	public void orderBetting(ITestContext context) {
		String filePath = "./src/test/resources/res/AHK3BetDatas.xml";
		BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "9");
		//投注
		betAPIHelper.betLottery();
	}
}
