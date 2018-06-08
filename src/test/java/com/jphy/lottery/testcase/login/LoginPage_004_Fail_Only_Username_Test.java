package com.jphy.lottery.testcase.login;

import com.jphy.lottery.pages.LoginPage;
import com.jphy.lottery.pages.pageshelper.AlertPageHelper;
import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.plugins.father.LoginFather;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jphy.lottery.util.PropertiesDataProvider;

/**
 * @author Lance
 * @Description 登陆失败-只输入用户名不输入密码
 * */
public class LoginPage_004_Fail_Only_Username_Test extends LoginFather {

	@Test
	public void loginFailTest_Username(ITestContext context) {
		String configFilePath = context.getCurrentXmlTest().getParameter("userInfoPath");
		String username = PropertiesDataProvider.getTestData(configFilePath, "username");
		LoginFather.loginParpare(context, seleniumUtil);
		LoginPageHelper.login(seleniumUtil, username, LoginPage.LP_INPUT_USERNAME);
		AlertPageHelper.checkAlertInfo(seleniumUtil, waitMillisecondsForAlert);
	}

}
