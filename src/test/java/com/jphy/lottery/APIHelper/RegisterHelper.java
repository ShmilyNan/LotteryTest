package com.jphy.lottery.APIHelper;

import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

public class RegisterHelper {
    public static Logger logger = Logger.getLogger(RegisterHelper.class.getName());
    private static String interface_register;
    private static String pwd;
    private static String phone;
    private static String code;
    private static String inviteCode;
    private static String url;
    private static String params;

    public RegisterHelper(ITestContext context,String phone,String inviteCode){
        interface_register = context.getCurrentXmlTest().getParameter("interface_register");
        pwd = PropertiesDataProvider.getTestData(interface_register, "pwd");
        code = PropertiesDataProvider.getTestData(interface_register, "messagecode");
        this.phone = phone;
        this.inviteCode = inviteCode;
        url = PropertiesDataProvider.getTestData(interface_register, "register_url");
    }

    public static void register(){
        params = "phone="+phone+"&pwd="+pwd+"&code="+code+"&inviteCode="+inviteCode;
        System.out.println(HttpUtils.doPost(url, params));
    }
}
