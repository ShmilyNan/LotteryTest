package com.jphy.lottery.APIHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jphy.lottery.plugins.ReadXml.BetField;
import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.PropertiesDataProvider;
import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.util.SeleniumUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;
import java.util.List;

public class BetAPIHelper {
    public static Logger logger = Logger.getLogger(BetAPIHelper.class.getName());
    private static SeleniumUtil seleniumUtil;
    private static String interface_bet = "";
    private static String filePath = "";
    private static List<BetField> betOrderList;
    private static String lotteryType = "";
    private static String number = "";
    private static String token = "";
    private static String resultNum = "";
    private static String resultOfBet = "";
    private static String bet_url = "";
    private static String resultOfUserInfo ="" ;
    private static String resultOfBetLog = "";

    public BetAPIHelper(ITestContext context,String filePath,String lotteryType,String number,String resultNum){
        this.filePath = filePath;
        this.lotteryType = lotteryType;
        this.number = number;
        this.resultNum = resultNum;
        seleniumUtil = new SeleniumUtil();
        interface_bet = context.getCurrentXmlTest().getParameter("interface_bet");
        betOrderList = new ReadXMLByDom4j().getBetOrders(new File(filePath));
        token = PropertiesDataProvider.getTestData(interface_bet, "token");
    }

    public static void betAndOpenLottery() {
        String str = "投注成功";
        for (int i = 0; i < betOrderList.size(); i++) {
            switch (str){
                case "投注成功":
                    bet(i);
                    str = getBetInfo(resultOfBet);
                    break;
            }
            //break;
        }
    }

    /**
     * 请求投注接口，获取返回信息
     */
    private static void bet(int i){
        JSONArray array = new JSONArray();
        JSONObject t = new JSONObject();
        t.put("betRange",betOrderList.get(i).getBetRange());
        t.put("playType",betOrderList.get(i).getPlayType());
        t.put("betContent",betOrderList.get(i).getBetContent());
        t.put("mutiple",betOrderList.get(i).getMutiple());
        if(betOrderList.get(i).getAmount() != null){
            t.put("amount",""+ betOrderList.get(i).getAmount() +"");
        }else{
            t.put("amount",null);
        }
        t.put("continuity",1);
        t.put("isWinStop",0);
        array.add(t);
        bet_url = PropertiesDataProvider.getTestData(interface_bet, "bet_url");
        String params_bet = "token=" + token + "&lotteryType=" + lotteryType + "&number=" + number + "&content=" + array.toString();
        resultOfBet = HttpUtils.doPost(bet_url, params_bet);
        System.out.println(resultOfBet);
        System.out.println(i);
        System.out.println(betOrderList.get(i).getBetRange()+betOrderList.get(i).getPlayType());
    }

    public static void openLottery(){
        String openLottery_url = PropertiesDataProvider.getTestData(interface_bet, "openLottery_url");
        String params_openLottery = "&lotteryType=" + Integer.parseInt(lotteryType) + "&number=" + number + "&resultNum=" + resultNum;
        HttpUtils.doPost(openLottery_url, params_openLottery);
        logger.info("==========Open Lottery Successed!============");
        seleniumUtil.isTextCorrect(getUserInfo(resultOfUserInfo), betOrderList.get(betOrderList.size()-1).getBalance());
    }


    private static String getBetInfo(String resultOfBet) {
        JSONObject json = JSONObject.parseObject(resultOfBet);
        return json.getString("msg");
    }

    private static String getUserInfo(String resultOfUserInfo) {
        String userInfo_url = PropertiesDataProvider.getTestData(interface_bet, "userInfo_url");
        String params_userInfo = "token=" + token;
        resultOfUserInfo = HttpUtils.doPost(userInfo_url, params_userInfo);

        JSONObject json = JSONObject.parseObject(resultOfUserInfo);
        JSONObject json2 = json.getJSONObject("data");
        return json2.getString("balance");
    }

    private static String getBetLogInfo(String resultOfBetLog, String key) {
        String betLog_url = PropertiesDataProvider.getTestData(interface_bet, "betLog_url");
        String params_betLog = "token=" + token + "&lotteryType=" + lotteryType + "&number=" + number;
        resultOfBetLog = HttpUtils.doPost(betLog_url, params_betLog);

        JSONObject json = JSONObject.parseObject(resultOfBetLog);
        JSONObject json2 = json.getJSONObject("data");
        JSONArray json3 = new JSONArray(json2.getJSONArray("logs"));
        JSONObject json4 = json3.getJSONObject(0);
        return json4.getString(key);
    }
}
