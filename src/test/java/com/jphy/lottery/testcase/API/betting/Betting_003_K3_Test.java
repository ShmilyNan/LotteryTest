package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

/**
 * @author Lance
 * @Description K3投注接口测试
 * */
public class Betting_003_K3_Test {

	@Test(invocationCount = 100)
	public void orderBetting(ITestContext context) {
		String filePath = "./src/test/resources/res/K3BetDatas.xml";
		//long start = System.currentTimeMillis();
		//while (true) {
			BetAPIHelper betAPIHelper = new BetAPIHelper(context, filePath, "10");
			//投注
			betAPIHelper.betLottery();
		//	if ((System.currentTimeMillis() - start) > 3600000 * 10) {
		//		break;
		//	}
		//}
	}
}
