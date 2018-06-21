package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description PK10投注接口测试
 * */
public class Betting_004_PK10_Test {

	@Test
	public void orderBetting(ITestContext context) {
		String filePath = "./src/test/resources/res/PK10BetDatas.xml";
		BetAPIHelper betAPIHelper = new BetAPIHelper(
				context,filePath,"1","688335");
		//投注
		betAPIHelper.betLottery();
		//指定开奖
		//betAPIHelper.openLottery("");
	}
}
