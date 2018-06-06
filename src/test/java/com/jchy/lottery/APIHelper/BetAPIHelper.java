package com.jchy.lottery.APIHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jchy.lottery.util.*;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;
import java.util.List;

public class BetAPIHelper {
    public static Logger logger = Logger.getLogger(BetAPIHelper.class.getName());
    public static SeleniumUtil seleniumUtil = new SeleniumUtil();

    /**
     * @param context
     */
    public static void checkReturnData(ITestContext context) {

        List<Bet_API> betOrderList = new ReadXMLByDom4j().getBetOrders(new File("./src/test/resources/res/BetDatas.xml"));
        String betInfo = context.getCurrentXmlTest().getParameter("betInfo");
        String interface_bet = context.getCurrentXmlTest().getParameter("interface_bet");
        String token = PropertiesDataProvider.getTestData(betInfo, "token");
        String lotteryType = PropertiesDataProvider.getTestData(betInfo, "lotteryType");
        String number = PropertiesDataProvider.getTestData(betInfo, "number");
        String resultNum = PropertiesDataProvider.getTestData(betInfo, "resultNum");
        String bet_url = PropertiesDataProvider.getTestData(interface_bet, "bet_url");
        String betLog_url = PropertiesDataProvider.getTestData(interface_bet, "betLog_url");
        String openLottery_url = PropertiesDataProvider.getTestData(interface_bet, "openLottery_url");
        String userInfo_url = PropertiesDataProvider.getTestData(interface_bet, "userInfo_url");
        String params_betLog = "token=" + token + "&lotteryType=" + lotteryType + "&number=" + number;
        String params_openLottery = "&lotteryType=" + Integer.parseInt(lotteryType) + "&number=" + number + "&resultNum=" + resultNum;
        String params_userInfo = "token=" + token;
        String resultOfUserInfo ="" ;
        String resultOfBetLog = "";
        String resultOfBet = "";

        String str = "投注成功";
        for (int i = 0; i < betOrderList.size(); i++) {
            //String content = PropertiesDataProvider.getTestData(betInfo, "content");

            String contentParam = "[{\"betRange\":" + "\"" + betOrderList.get(i).getBetRange() + "\"" + ",\"playType\":" + "\"" + betOrderList.get(i).getPlayType() + "\"" + ",\"betContent\":" + "\"" + betOrderList.get(i).getBetContent() + "\"" + ",\"mutiple\":" + betOrderList.get(i).getMutiple() + ",\"amount\":" + "\"" + betOrderList.get(i).getAmount() + "\"" + ",\"continuity\":1,\"isWinStop\": 0}]";
            String params_bet = "token=" + token + "&lotteryType=" + lotteryType + "&number=" + number + "&content=" + contentParam;

            switch (str){
                case "投注成功":
                    //System.out.println(contentParam);
                    //投注
                    //resultOfBet = HttpUtils.doPost(bet_url, params_bet);
                    //System.out.println(resultOfBet);
                    //开奖
                    HttpUtils.doPost(openLottery_url, params_openLottery);
                    //投注记录
                    //resultOfBetLog = HttpUtils.doPost(betLog_url, params_betLog);
                    //个人信息
                    //resultOfUserInfo = HttpUtils.doPost(userInfo_url, params_userInfo);
                    //System.out.println(i);
                    break;
                default:
                    break;
            }
            str = getBetInfo(resultOfBet);
        }
        System.out.println(resultOfBetLog);
        seleniumUtil.isTextCorrect(getUserInfo(resultOfUserInfo), betOrderList.get(betOrderList.size()-1).getBalance());
    }


    public static String getBetInfo(String resultOfBet) {
        JSONObject json = JSONObject.parseObject(resultOfBet);
        return json.getString("msg");
    }

    public static String getUserInfo(String resultOfUserInfo) {
        JSONObject json = JSONObject.parseObject(resultOfUserInfo);
        JSONObject json2 = json.getJSONObject("data");
        return json2.getString("balance");
    }

    public static String getBetLogInfo(String resultOfBetLog, String key) {
        JSONObject json = JSONObject.parseObject(resultOfBetLog);
        JSONObject json2 = json.getJSONObject("data");
        JSONArray json3 = new JSONArray(json2.getJSONArray("logs"));
        JSONObject json4 = json3.getJSONObject(0);
        return json4.getString(key);
    }


}
