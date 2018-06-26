package com.jphy.lottery.testcase.API.betting;

import com.jphy.lottery.APIHelper.BetAPIHelper;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description PK10投注接口测试
 * */
public class Betting_010_All_Test {
	public static Logger logger = Logger.getLogger(Betting_010_All_Test.class.getName());

	@Test(invocationCount = 10000)
	public void orderBetting(ITestContext context) {
		long start = System.currentTimeMillis();

		while (true){

			String filePath1 = "./src/test/resources/res/AMFFCBetDatas.xml";
			BetAPIHelper betAPIHelper1 = new BetAPIHelper(context, filePath1, "6");
			//投注
			if(betAPIHelper1.getCanbet()){
				betAPIHelper1.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath2 = "./src/test/resources/res/AMWFCBetDatas.xml";
			BetAPIHelper betAPIHelper2 = new BetAPIHelper(context, filePath2, "5");
			//投注
			if(betAPIHelper2.getCanbet()){
				betAPIHelper2.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath3 = "./src/test/resources/res/CQSSCBetDatas.xml";
			BetAPIHelper betAPIHelper3 = new BetAPIHelper(context, filePath3, "0");
			//投注
			if(betAPIHelper3.getCanbet()){
				betAPIHelper3.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath4 = "./src/test/resources/res/XJSSCBetDatas.xml";
			BetAPIHelper betAPIHelper4 = new BetAPIHelper(context, filePath4, "4");
			//投注
			if(betAPIHelper4.getCanbet()){
				betAPIHelper4.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath5 = "./src/test/resources/res/5Of11BetDatas.xml";
			BetAPIHelper betAPIHelper5 = new BetAPIHelper(context, filePath5, "11");
			//投注
			if(betAPIHelper5.getCanbet()){
				betAPIHelper5.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath6 = "./src/test/resources/res/AHK3BetDatas.xml";
			BetAPIHelper betAPIHelper6 = new BetAPIHelper(context, filePath6, "9");
			//投注
			if(betAPIHelper6.getCanbet()){
				betAPIHelper6.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath7 = "./src/test/resources/res/AMK3BetDatas.xml";
			BetAPIHelper betAPIHelper7 = new BetAPIHelper(context, filePath7, "10");
			//投注
			if(betAPIHelper7.getCanbet()){
				betAPIHelper7.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath8 = "./src/test/resources/res/JSK3BetDatas.xml";
			BetAPIHelper betAPIHelper8 = new BetAPIHelper(context, filePath8, "8");
			//投注
			if(betAPIHelper8.getCanbet()){
				betAPIHelper8.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			String filePath9 = "./src/test/resources/res/PK10BetDatas.xml";
			BetAPIHelper betAPIHelper9 = new BetAPIHelper(context, filePath9, "1");
			//投注
			if(betAPIHelper9.getCanbet()){
				betAPIHelper9.betLottery();
			}else {
				logger.info("当前期已投注！");
			}

			if (System.currentTimeMillis()-start>36000000){
				break;
			}
		}
	}
}
