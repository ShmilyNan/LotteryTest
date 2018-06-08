package com.jphy.lottery.plugins.father;

import com.jphy.lottery.base.BaseParpare;
import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.util.SeleniumUtil;
import org.testng.ITestContext;

/**
 * @author Lance
 * @description 每个流程 开始之间的一个准备类，主要是做登录准备以及相关数据加载准备
 * 
 * */
public class LoginFather extends BaseParpare {
	protected static int timeOut = 0;
	protected static int sleepTime = 0;
	protected static int waitMillisecondsForAlert = 0;

	/**
	 * 登录相关准备操作
	 * */
	public static void loginParpare(ITestContext context, SeleniumUtil seleniumUtil) {
		timeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		sleepTime = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		waitMillisecondsForAlert = Integer.valueOf(context.getCurrentXmlTest().getParameter("waitMillisecondsForAlert"));
//		HomePageHelper.waitHomePageToLoad(timeOut, seleniumUtil);
//		HomePageHelper.enterPage(seleniumUtil, HomePage.HP_BUTTON_LOGIN);
		LoginPageHelper.waitLoginPageToLoad(timeOut, seleniumUtil);
		LoginPageHelper.checkLoginPageText(seleniumUtil);
	}
}
