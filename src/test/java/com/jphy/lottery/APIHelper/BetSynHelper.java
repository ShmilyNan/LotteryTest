package com.jphy.lottery.APIHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jphy.lottery.plugins.ReadXml.BetOrder;
import com.jphy.lottery.plugins.ReadXml.OrderRebate;
import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 投注接口测试
 */
public class BetSynHelper {
    public static Logger logger = Logger.getLogger(BetSynHelper.class.getName());
    private static SeleniumUtil seleniumUtil;
    private static String interface_bet;
    private static String filePath;
    private static List<BetOrder> betOrderList;
    private static List<OrderRebate> orderRebate;
    private static String lotteryType;
    private static String token;
    private static String number;
    private static String resultNum;
    private static String resultOfBet;
    private static String bet_url;
    private static String bet_Total_Amount;//花费
    private static String win_Amount;
    private static String rebateAll;//返利
    private static String balance;
    private static String lotteryOrder;

    private static Map<String, Object> betMap = new HashMap<>();

    private boolean canBet = false;//是否可以投注，true:能投注,false:不能投注

    public void setCanBet(boolean canBet) {
        this.canBet = canBet;
    }

    public boolean getCanbet() {
        return this.canBet;
    }


    /**
     * 初始化全局变量
     *
     * @param context
     * @param filePath
     * @param lotteryType
     */
    public BetSynHelper(ITestContext context, String filePath, String lotteryType, String number) {
        this.filePath = filePath;
        this.lotteryType = lotteryType;
        String mark = lotteryType + "" + number;
        if (betMap.get(mark) != null) {//已经投注过
            this.canBet = false;
        } else {//还未投注
            this.number = number;
            seleniumUtil = new SeleniumUtil();
            interface_bet = context.getCurrentXmlTest().getParameter("interface_bet");
            betOrderList = new ReadXMLByDom4j().getBetOrders(new File(filePath));
            resultNum = betOrderList.get(0).getResultNum();
            token = PropertiesDataProvider.getTestData(interface_bet, "token");
            betMap.put(mark, number);
            this.canBet = true;
        }
    }

    public static void betLottery() {
        int len = betOrderList.size();
        //int len = 10;
        for (int i = 0; i < len; i++) {
            boolean success = bet(i, number);
            if (!success) {
                logger.info("彩种：" + lotteryType + ", 期号：" + number + "投注结束，共投：" + (i + 1) + "单");
                break;
            }

            String str = getBetInfo(resultOfBet);
            logger.info("彩种：" + lotteryType + ", 期号：" + number + "第" + (i + 1) + "单投注响应结果：" + resultOfBet);
            if (str.equals("投注成功")) {
                continue;
            } else {
                logger.info("彩种：" + lotteryType + ", 期号：" + number + "投注结束，共投：" + (i + 1) + "单");
                if (lotteryType.equals("")&&i == (len - 1)) {
                    logger.info("记录当期期号，后期方便后期比对订单数据");
                    ExcelDataProviderByPoi edpbPoi = new ExcelDataProviderByPoi();
                    edpbPoi.updateExcel("./src/test/resources/data/K3BetDatas.xml",1,0,number);
                }
                break;
            }
        }
    }

    /**
     * 请求投注接口，获取返回信息
     */
    private static boolean bet(int i, String number) {
        JSONArray array = new JSONArray();
        JSONObject t = new JSONObject();
        t.put("betRange", betOrderList.get(i).getBetRange());
        t.put("playType", betOrderList.get(i).getPlayType());
        t.put("betContent", betOrderList.get(i).getBetContent());
        if (betOrderList.get(i).getMutiple() != null) {
            t.put("mutiple", betOrderList.get(i).getMutiple());
        } else {
            t.put("mutiple", null);
        }
        if (betOrderList.get(i).getAmount() != null) {
            t.put("amount", betOrderList.get(i).getAmount());
        } else {
            t.put("amount", null);
        }
        t.put("continuity", 1);
        t.put("isWinStop", 0);
        array.add(t);
        bet_url = PropertiesDataProvider.getTestData(interface_bet, "bet_url");

        int rand = (int) (Math.random() * 91) + 10;
        String tokenQ = PropertiesDataProvider.getTestData(interface_bet, "tokenQ");
        String tokenH = PropertiesDataProvider.getTestData(interface_bet, "tokenH");
        //token = tokenQ +rand+ tokenH;
        token = PropertiesDataProvider.getTestData(interface_bet, "token");

        String params_bet = "token=" + token + "&lotteryType=" + lotteryType + "&number=" + number + "&content=" + array.toString();

        System.out.println(betOrderList.get(i).getBetRange() + betOrderList.get(i).getPlayType());
        boolean success = true;

        resultOfBet = HttpUtils.doPost(bet_url, params_bet);
        if (resultOfBet == null) {
            logger.info("彩种：" + lotteryType + ", 期号：" + number + "第" + (i + 1) + "单第一次投注失败,尝试第二次投注！");
            resultOfBet = HttpUtils.doPost(bet_url, params_bet);
            if (resultOfBet == null) {
                logger.info("彩种：" + lotteryType + ", 期号：" + number + "第" + (i + 1) + "单第二次投注失败，该订单投注结束！");
                success = false;
            }
        }
        return success;
    }

    private static String getBetInfo(String resultOfBet) {
        JSONObject json = JSONObject.parseObject(resultOfBet);
        return json.getString("msg");
    }
}