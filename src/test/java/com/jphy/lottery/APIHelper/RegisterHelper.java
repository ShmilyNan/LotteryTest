package com.jphy.lottery.APIHelper;

import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

public class RegisterHelper {
    public static Logger logger = Logger.getLogger(RegisterHelper.class.getName());
    private static String pwd;
    private static String username;
    private static String inviteCode;
    private static String url;
    private static String params;

    public static void register(){
        url = "http://103.37.61.78:8081/appservice/platform/register";
        pwd = "FE32DB846C24E3C91051F872F4319551";
        inviteCode = "";
        for (int i =11;i<=100;){
            if (i<10){
                username = "test00"+i;
            }else {
                username = "test0"+i;
            }
            params = "username="+username+"&pwd="+pwd+"&inviteCode="+inviteCode;
            System.out.println(HttpUtils.doPost(url, params));
        }
    }
}
