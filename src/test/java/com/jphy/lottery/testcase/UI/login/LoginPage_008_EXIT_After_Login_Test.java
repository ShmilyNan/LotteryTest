package com.jphy.lottery.testcase.UI.login;

/**
 *@author  Lance
 *@Description 测试用例：退出登陆操作
 * */
import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.plugins.father.LoginFather;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jphy.lottery.util.PropertiesDataProvider;

public class LoginPage_008_EXIT_After_Login_Test extends LoginFather {

	@Test
	public void exitLoginTest(ITestContext context) {
		String configFilePath = context.getCurrentXmlTest().getParameter("userInfoPath");
		String username = PropertiesDataProvider.getTestData(configFilePath, "username");
		String password = PropertiesDataProvider.getTestData(configFilePath, "password");
		LoginFather.loginParpare(context, seleniumUtil);
		LoginPageHelper.checkLoginPageText(seleniumUtil);
		LoginPageHelper.login(seleniumUtil, username, password);
		LoginPageHelper.checkUserInfo(timeOut, sleepTime, seleniumUtil, username);
//		HomePageHelper.enterPage(seleniumUtil, HomePage.HP_TEXT_USERINFO);
//		seleniumUtil.click(seleniumUtil.findElementBy(HomePage.HP_LINK_EXIT));
	}

}
