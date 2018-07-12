package com.jphy.lottery.testcase.API.register;

import com.jphy.lottery.APIHelper.RegisterHelper;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description 注册接口测试
 * */
public class Register_001_Register_Success_Test {

	@Test
	public void accountRegistration(ITestContext context) {
		RegisterHelper registerHelper = new RegisterHelper();
		registerHelper.register();
	}
}
