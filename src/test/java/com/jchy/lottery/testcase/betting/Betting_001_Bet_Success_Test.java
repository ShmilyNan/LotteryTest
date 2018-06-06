package com.jchy.lottery.testcase.betting;

import com.jchy.lottery.APIHelper.BetAPIHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

/**
 * @author Lance
 * @Description 投注接口测试
 * */
public class Betting_001_Bet_Success_Test {

	@Test
	public void orderBetting(ITestContext context) throws FileNotFoundException {
		BetAPIHelper.checkReturnData(context);
	}

}
