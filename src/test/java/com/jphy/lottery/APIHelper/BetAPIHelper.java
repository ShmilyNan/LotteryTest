package com.jphy.lottery.APIHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jphy.lottery.plugins.ReadXml.BetOrder;
import com.jphy.lottery.plugins.ReadXml.OrderRebate;
import com.jphy.lottery.util.HttpUtils;
import com.jphy.lottery.util.JdbcUtil;
import com.jphy.lottery.util.PropertiesDataProvider;
import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.util.SeleniumUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;
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
    private static List<BetOrder> betOrderList;
    private static List<OrderRebate> orderRebate;
    private static String lotteryType;
    private static String token;
    private static String number;
    private static String getNumber;
    private static String resultNum;
    private static String resultOfBet;
    private static String bet_url;
    private static String bet_Total_Amount;//花费
    private static String win_Amount;
    private static String rebateAll;//返利
    private static String balance;
    private static String lotteryOrder;

    /**
     * 初始化全局变量
     * @param context
     * @param filePath
     * @param lotteryType
     */
    public BetAPIHelper(ITestContext context,String filePath,String lotteryType){
        this.filePath = filePath;
        this.lotteryType = lotteryType;
        getNumber = String.format("SELECT number FROM basic_number WHERE LOTTERY_TYPE = %d AND CREATE_TIME < NOW() AND MODIFY_TIME > NOW()",Integer.parseInt(lotteryType));
        this.number = JdbcUtil.query(getNumber,"number");
        seleniumUtil = new SeleniumUtil();
        interface_bet = context.getCurrentXmlTest().getParameter("interface_bet");
        betOrderList = new ReadXMLByDom4j().getBetOrders(new File(filePath));
        resultNum = betOrderList.get(0).getResultNum();
        token = PropertiesDataProvider.getTestData(interface_bet, "token");
    }

    public static void betLottery() {
        String str = "投注成功";
        for (int i = 0; i < betOrderList.size(); i++) {
            try {
                switch (str) {
                    case "投注成功":
                        bet(i, number);
                        str = getBetInfo(resultOfBet);
                        if (str.equals("当前期号不能投注")) {
                            number = JdbcUtil.query(getNumber, "number");
                        }
                        logger.info(betOrderList.get(i).getBetRange() + betOrderList.get(i).getPlayType());
                        logger.info(resultOfBet);
                        break;
                    default:
                        bet(i, number);
                        str = getBetInfo(resultOfBet);
                        if (str.equals("当前期号不能投注")) {
                            number = JdbcUtil.query(getNumber, "number");
                        }
                        logger.info(betOrderList.get(i).getBetRange() + betOrderList.get(i).getPlayType());
                        logger.info(resultOfBet);
                }
                //break;
            }catch (Exception e){
                logger.error(e);
            }
        }
    }

    /**
     * 请求投注接口，获取返回信息
     */
    private static void bet(int i,String number){
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
        System.out.println(i);
        //selectBetOrder(i);
        //logger.info("==========start check Spend!=============");
        //seleniumUtil.isTextCorrect(bet_Total_Amount,betOrderList.get(i).getSpend());

    }

    /**
     * 开奖，获取中奖信息
     */
    public static void openLottery(String id){
        String openLottery_url = PropertiesDataProvider.getTestData(interface_bet, "openLottery_url");
        String params_openLottery = "&lotteryType=" + Integer.parseInt(lotteryType) + "&id=" + id + "&result=" + resultNum;
        HttpUtils.doPost(openLottery_url, params_openLottery);
        //checkDatas(0,true);
        for (int i = 0 ;i<betOrderList.size();i++ ){
            logger.info("==========start check Win_Amount!=============");
            seleniumUtil.isTextCorrect(win_Amount,betOrderList.get(i).getDrawnAmount());
            logger.info("==========start check UPPER_POINTS!=============");
            seleniumUtil.isTextCorrect(rebateAll,orderRebate.get(i).getRebateAll());
            break;
        }
        logger.info("==========start check balance!=============");
        //seleniumUtil.isTextCorrect(balance,betOrderList.get(0).getBalance());
        //seleniumUtil.isTextCorrect(balance,betOrderList.get(betOrderList.size()-1).getBalance());
    }

    /**
     * 投注、开奖后在数据库中查询投注、开奖信息
     * @param i
     */
    public static void selectBetOrder(int i){
        lotteryOrder = String.format(
                "SELECT * FROM lottery_order WHERE (USER_ID = 151 AND BET_RANGE = '%s' AND LOTTERY_TYPE = %s AND PLAY_TYPE = '%s')",
                betOrderList.get(i).getUser_id(),betOrderList.get(i).getBetRange(),lotteryType,betOrderList.get(i).getPlayType());
        //bet_Total_Amount = JdbcUtil.query(lotteryOrder,"bet_Total_Amount");
        //win_Amount = JdbcUtil.query(lotteryOrder,"win_Amount");
        //rebateAll = JdbcUtil.query(lotteryOrder,"UPPER_POINTS");
    }

    /**
     * @param i
     */
    public static void getBalance(int i){
        String queryBalance = String.format("SELECT * FROM lottery_user_account WHERE USER_ID = %d",betOrderList.get(i).getUser_id());
        balance = JdbcUtil.query(queryBalance,"number");
    }

    private static String getBetInfo(String resultOfBet) {
        JSONObject json = JSONObject.parseObject(resultOfBet);
        return json.getString("msg");
    }

    /*
    private static String getUserInfo(String resultOfUserInfo) {
        String userInfo_url = PropertiesDataProvider.getTestData(interface_bet, "userInfo_url");
        String params_userInfo = "token=" + token;
        resultOfUserInfo = HttpUtils.doPost(userInfo_url, params_userInfo);

        JSONObject json = JSONObject.parseObject(resultOfUserInfo);
        JSONObject json2 = json.getJSONObject("data");
        return json2.getString("balance");
    }
*/
    /*
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
    */
}
