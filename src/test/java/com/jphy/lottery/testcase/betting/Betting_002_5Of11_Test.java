package com.jphy.lottery.testcase.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

/**
 * @author Lance
 * @Description 11选5投注接口测试
 * */
public class Betting_002_5Of11_Test {

	@Test
	public void orderBetting(ITestContext context) {
		BetAPIHelper betAPIHelper = new BetAPIHelper(context,"./src/test/resources/res/5of11BetDatas.xml");
		betAPIHelper.checkReturnData();
	}

}
