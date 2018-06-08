package com.jphy.lottery.testcase.login;

/**
 *@author  Lance
 *@Description 测试用例：登陆界面复选框是不是可以勾选
 *
 * */
import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.plugins.father.LoginFather;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class LoginPage_009_Checkbox_Test extends LoginFather {
	@Test
	public void uiTest(ITestContext context) {
		LoginFather.loginParpare(context, seleniumUtil);
		LoginPageHelper.doesCheckBoxSelect(seleniumUtil);
		
	}

}
