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
        url = "http://116.31.118.18:8080/lottery/appservice/platform/register";
        //url = "http://192.168.1.199:8080/appservice/platform/register";
        pwd = "FE32DB846C24E3C91051F872F4319551";
        inviteCode = "001000001";
        for (int i =2;i<=2;i++){
            if (i<10){
                username = "test00"+i;
            }else if(i>9&&i<100) {
                username = "test0"+i;
            }
            params = "username="+username+"&pwd="+pwd+"&inviteCode="+inviteCode;
            System.out.println(HttpUtils.doPost(url, params));
        }
    }
}
