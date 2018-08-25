package com.jphy.lottery.APIHelper;

import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;

public class RegisterHelper {
    public static Logger logger = Logger.getLogger(RegisterHelper.class.getName());
    private static String interface_register;
    private static String pwd;
    private static String username;
    private static String inviteCode;
    private static String params;
    private static String register_url;

    public RegisterHelper(ITestContext context) {
        interface_register = context.getCurrentXmlTest().getParameter("interface_register");
        register_url = PropertiesDataProvider.getTestData(interface_register, "register_url");
        inviteCode = PropertiesDataProvider.getTestData(interface_register, "inviteCode");
        pwd = PropertiesDataProvider.getTestData(interface_register, "pwd");
    }

    public static void register() {
        for (int i = 1; i <= 1; i++) {
            if (i < 10) {
                username = "test00" + i;
            } else if (i > 9 && i < 100) {
                username = "test0" + i;
            }
            params = "username=" + username + "&pwd=" + pwd + "&inviteCode=" + inviteCode;
            System.out.println(HttpUtils.doPost(register_url, params));
        }
    }
}
