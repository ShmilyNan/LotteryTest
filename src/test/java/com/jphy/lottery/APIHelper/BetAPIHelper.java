package com.jphy.lottery.APIHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jphy.lottery.plugins.ReadXml.BetField;
import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.JdbcUtil;
import com.jphy.lottery.util.PropertiesDataProvider;
import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.util.SeleniumUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 投注接口测试
 */
public class BetAPIHelper {
    public static Logger logger = Logger.getLogger(BetAPIHelper.class.getName());
    private static SeleniumUtil seleniumUtil;
    private static String interface_bet;
    private static String filePath;
    private static List<BetField> betOrderList;
    private static String lotteryType;
    private static String number;
    private static String token;
    private static String resultNum;
    private static String resultOfBet;
    private static String bet_url;
    private static String bet_Total_Amount;
    private static String win_Amount;
    private static String balance;

    /**
     * 初始化全局变量
     * @param context
     * @param filePath
     * @param lotteryType
     * @param number
     * @param resultNum
     */
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

    public static void betLottery() {
        String str = "投注成功";
        for (int i = 0; i < betOrderList.size(); i++) {
            switch (str){
                case "投注成功":
                    bet(i);
                    str = getBetInfo(resultOfBet);
                    break;
                case "投注成功1":
                    String str3 = betOrderList.get(0).getPlayType();
                    System.out.println(str3);
                    String str2 = betOrderList.get(0).getDrawnAmount();
                    System.out.println(str2);
                    break;
            }
            break;
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
        if(betOrderList.get(i).getMutiple() != null){
            t.put("mutiple",betOrderList.get(i).getMutiple());
        }else{
            t.put("mutiple",null);
        }
        if(betOrderList.get(i).getAmount() != null){
            t.put("amount",betOrderList.get(i).getAmount());
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
        checkDatas(i,false);
        logger.info("==========start check Spend!=============");
        seleniumUtil.isTextCorrect(bet_Total_Amount,betOrderList.get(i).getSpend());

    }

    /**
     * 开奖，获取中奖信息
     */
    public static void openLottery(){
        String openLottery_url = PropertiesDataProvider.getTestData(interface_bet, "openLottery_url");
        String params_openLottery = "&lotteryType=" + Integer.parseInt(lotteryType) + "&number=" + number + "&resultNum=" + resultNum;
        HttpUtils.doPost(openLottery_url, params_openLottery);
        checkDatas(0,true);
        for (int i = 0 ;i<betOrderList.size() ;i++ ){
            logger.info("==========start check Win_Amount!=============");
            seleniumUtil.isTextCorrect(win_Amount,betOrderList.get(i).getDrawnAmount());
            break;
        }
        logger.info("==========start check balance!=============");
        seleniumUtil.isTextCorrect(balance,betOrderList.get(0).getBalance());
        //seleniumUtil.isTextCorrect(balance,betOrderList.get(betOrderList.size()-1).getBalance());
    }

    /**
     * 投注、开奖后在数据库中查询投注、开奖信息
     * @param i
     */
    public static void checkDatas(int i,boolean isOpen){
        Map<String,String> map1 = null;
        Map<String,String> map2 = null;
        String betSql = String.format(
                "SELECT * FROM lottery_order WHERE (USER_ID = 35 AND BET_RANGE = '%s' AND LOTTERY_TYPE = %s AND PLAY_TYPE = '%s')",
                betOrderList.get(i).getBetRange(),lotteryType,betOrderList.get(i).getPlayType());
        String openSql = "SELECT * FROM lottery_user_account WHERE USER_ID = 35";
        if (!isOpen){
            map1 = JdbcUtil.query(betSql,false);
            bet_Total_Amount = map1.get("bet_Total_Amount");
        }else {
            map1 = JdbcUtil.query(betSql,false);
            win_Amount = map1.get("win_Amount");
            map2 = JdbcUtil.query(openSql,true);
            balance = map2.get("balance");
        }
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
