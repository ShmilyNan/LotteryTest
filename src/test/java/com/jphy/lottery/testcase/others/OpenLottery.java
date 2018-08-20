package com.jphy.lottery.testcase.others;

import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class OpenLottery {
    public static Logger logger = Logger.getLogger(OpenLottery.class.getName());
    public static List<String> numbers = null;

    @Parameters({"lotteryType", "type", "waitTime"})
    @Test(invocationCount = 1)
    public void openLottery(int lotteryType, int type, long waitTime) throws Exception {
        if (type == 0) {
            insert(lotteryType);
        } else if (type == 1) {
            update(lotteryType, waitTime);
        }
    }

    public void insert(int lotteryType) {
        JdbcUtil.insertNumbers(lotteryType);
    }

    public void update(int lotteryType, long waitTime) throws Exception {
        int orders = 0;
        if (lotteryType == 0 || (lotteryType >= 4 && lotteryType <= 6)) {
            orders = 92;
        } else if (lotteryType == 11) {
            orders = 37;
        } else if (lotteryType >= 8 && lotteryType <= 10) {
            orders = 10;
        } else if (lotteryType == 1) {
            orders = 12;
        }
        while (true) {
            numbers = JdbcUtil.queryNumbersToUpdateResult(lotteryType, orders);
            //numbers = new ArrayList<>();
            //numbers.add("9090910");
            if (numbers.size() == 0) {
                //JdbcUtil.updateResult(lotteryType, numbers);
                Thread.sleep(waitTime);
                //break;
            } else {
                JdbcUtil.updateResult(lotteryType, numbers);
                //break;
            }
        }
    }
}
