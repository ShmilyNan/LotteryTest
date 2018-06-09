package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 * */
public class Betting_004_PK10_Test {

	@Test
	public void orderBetting(ITestContext context) {
		String filePath = "./src/test/resources/res/PK10BetDatas.xml";
		BetAPIHelper betAPIHelper = new BetAPIHelper(
				context,filePath,"1","3","08,09,01,02,03,05,06,10,07,04");
		betAPIHelper.betAndOpenLottery();
	}
}
