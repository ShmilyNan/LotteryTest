package com.jphy.lottery.testcase.UI.login;

/**
 * @author Lance
 * @Description 测试用例：登陆界面UI测试
 */

import com.jphy.lottery.pages.pageshelper.LoginPageHelper;
import com.jphy.lottery.plugins.father.LoginFather;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class LoginPage_001_UI_Test extends LoginFather {
    @Test
    public void uiTest(ITestContext context) {
        LoginFather.loginParpare(context, seleniumUtil);
        LoginPageHelper.checkInputData(seleniumUtil);
    }
}
