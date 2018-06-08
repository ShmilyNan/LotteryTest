package com.jphy.lottery.testcase.login;

import com.jphy.lottery.pages.pageshelper.AlertPageHelper;
import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.plugins.father.LoginFather;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.jphy.lottery.util.PropertiesDataProvider;

/**
 * @author Lance
 * @Description 登陆失败-输入正确用户名，密码输入错误,提示“用户名或密码错误,请重新登录!”
 * */
public class LoginPage_007_Fail_Incorrect_Password_Test extends LoginFather {

	@Test
	public void loginFailTest_incorrectPassword(ITestContext context) {
		String configFilePath = context.getCurrentXmlTest().getParameter("userInfoPath");
		String username = PropertiesDataProvider.getTestData(configFilePath, "username");
		String incorrectpassword = PropertiesDataProvider.getTestData(configFilePath, "incorrectpassword");
		LoginFather.loginParpare(context, seleniumUtil);
		LoginPageHelper.login(seleniumUtil, username, incorrectpassword);
		AlertPageHelper.checkAlertInfo(seleniumUtil, waitMillisecondsForAlert);
	}

}
