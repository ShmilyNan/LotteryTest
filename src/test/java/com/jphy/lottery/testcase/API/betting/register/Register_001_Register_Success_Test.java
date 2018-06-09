package com.jphy.lottery.testcase.API.betting.register;

import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.testng.ITestContext;
import org.testng.annotations.Test;

/**
 * @author Lance
 * @Description 注册接口测试
 * */
public class Register_001_Register_Success_Test {

	@Test
	public void orderBetting(ITestContext context) {
		String registerInfo = context.getCurrentXmlTest().getParameter("registerInfo");
		String interface_register = context.getCurrentXmlTest().getParameter("interface_register");
		String inviteCode = PropertiesDataProvider.getTestData(registerInfo, "inviteCode");
		String phone = PropertiesDataProvider.getTestData(registerInfo, "phone");
		String pwd = PropertiesDataProvider.getTestData(registerInfo, "pwd");
		String code = PropertiesDataProvider.getTestData(registerInfo, "code");
		String url = PropertiesDataProvider.getTestData(interface_register, "register_url");
		String params ="phone="+phone+"&pwd="+pwd+"&code="+code+"&inviteCode="+inviteCode;
//		HttpRequestUtil.post(url,params);
		System.out.println(HttpUtils.doPost(url, params));
	}
}
