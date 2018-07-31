package com.jphy.lottery.testcase.others;

import com.jphy.lottery.util.JdbcUtil;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.List;

public class OpenLottery {
    public static Logger logger = Logger.getLogger(OpenLottery.class.getName());

    @Test(invocationCount = 1)
    public void orderBetting() throws Exception {
        final int lotteryType = 4;
        int orders = 92;
        //JdbcUtil.insertNumbers(lotteryType);
        while (true) {
            List<String> numbers = JdbcUtil.queryNumbersToUpdateResult(lotteryType, orders);
            if (numbers.size() == 0) {
                Thread.sleep(60000);
            } else {
                for (int i = 0; i < numbers.size(); i++) {
                    JdbcUtil.updateResult(lotteryType, numbers.get(i));
                }
            }
        }
    }
}
