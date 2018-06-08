package com.jphy.lottery.testcase.login;

/**
 *@author  Lance
 *@Description 测试用例：成功登陆
 * */
import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.plugins.father.LoginFather;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jphy.lottery.util.PropertiesDataProvider;

public class LoginPage_002_Login_Success_Test extends LoginFather {

	@Test
	public void loginSuccessTest(ITestContext context) {
		String userInfoPath = context.getCurrentXmlTest().getParameter("userInfoPath");
		String username = PropertiesDataProvider.getTestData(userInfoPath, "username");
		String password = PropertiesDataProvider.getTestData(userInfoPath, "password");
		LoginFather.loginParpare(context, seleniumUtil);
		LoginPageHelper.login(seleniumUtil, username, password);
		LoginPageHelper.checkUserInfo(timeOut, sleepTime, seleniumUtil, username);
	}

}
