package com.jphy.lottery.APIHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jphy.lottery.plugins.ReadXml.BetOrder;
import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.util.HttpAsyncClientUtil;
import com.jphy.lottery.util.PropertiesDataProvider;
import org.apache.http.NameValuePair;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 投注接口测试
 */
public class BetAsynHelper {
    public static Logger logger = Logger.getLogger(BetAsynHelper.class.getName());
    public static HttpAsyncClientUtil httpAsyncClientUtil = new HttpAsyncClientUtil();
    private static String interface_bet;
    private static List<BetOrder> betOrderList;
    private static String lotteryType;
    private static String token;
    private static String bet_url;

    /**
     * 初始化全局变量
     *
     * @param context
     * @param filePath
     * @param lotteryType
     */
    public BetAsynHelper(ITestContext context, String filePath, String lotteryType) {
        interface_bet = context.getCurrentXmlTest().getParameter("interface_bet");
        this.lotteryType = lotteryType;
        bet_url = PropertiesDataProvider.getTestData(interface_bet, "bet_url");
        token = PropertiesDataProvider.getTestData(interface_bet, "token");
        betOrderList = new ReadXMLByDom4j().getBetOrders(new File(filePath));
    }

    public static void betLottery(CloseableHttpAsyncClient httpClient, String number) {
        CountDownLatch latch = new CountDownLatch(betOrderList.size());
        for (int i = 0; i < betOrderList.size(); i++) {
            bet(i, number, httpClient, latch);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("第" + number + "期完成！");
    }

    /**
     * 请求投注接口，获取返回信息
     */
    private static void bet(int i, String number, CloseableHttpAsyncClient httpClient, CountDownLatch latch) {
        JSONArray array = new JSONArray();

        JSONObject object = new JSONObject();
        object.put("betRange", betOrderList.get(i).getBetRange());
        object.put("playType", betOrderList.get(i).getPlayType());
        object.put("betContent", betOrderList.get(i).getBetContent());
        if (betOrderList.get(i).getMutiple() != null) {
            object.put("mutiple", betOrderList.get(i).getMutiple());
        } else {
            object.put("mutiple", null);
        }
        if (betOrderList.get(i).getAmount() != null) {
            object.put("amount", betOrderList.get(i).getAmount());
        } else {
            object.put("amount", null);
        }
        object.put("continuity", 1);
        object.put("isWinStop", 0);
        array.add(object);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));
        params.add(new BasicNameValuePair("lotteryType", lotteryType));
        params.add(new BasicNameValuePair("number", number));
        params.add(new BasicNameValuePair("content", array.toJSONString()));

        httpAsyncClientUtil.doPost(bet_url, params, lotteryType, number, httpClient, latch);
    }

}
