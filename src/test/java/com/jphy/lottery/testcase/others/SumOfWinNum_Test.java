package com.jphy.lottery.testcase.others;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jphy.lottery.util.ReadAndWriteExcelByPoi;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @author Lance
 * @Description 时时彩投注接口测试
 */
public class SumOfWinNum_Test {
    public static Logger logger = Logger.getLogger(SumOfWinNum_Test.class.getName());

    @Test
    public void sumOfWinNum() {
        ReadAndWriteExcelByPoi readAndWriteExcelByPoi = new ReadAndWriteExcelByPoi();
        String fileName = "CQSSCBetDatas";
        String path = "./src/test/resources/data/" + fileName + ".xlsm";

        String value = "";
        for (int i = 0; i <= 99999; i++) {
            if (i < 10) {
                value = "0000" + i;
            } else if (i < 100 && i >= 10) {
                value = "000" + i;
            } else if (i < 1000 && i >= 100) {
                value = "00" + i;
            } else if (i < 10000 && i >= 1000) {
                value = "0" + i;
            } else {
                value = "" + i;
            }
            readAndWriteExcelByPoi.updateExcel(path, 1, 10, value);
            double SumOfWinNum = Double.parseDouble(new DecimalFormat("#.###").format(readAndWriteExcelByPoi.getSumOfWinNum(path, 132, 12)));
            logger.info("第次"+(i+1)+"求和");
            readAndWriteExcelByPoi.updateExcel(path, i+1, 11, String.valueOf(SumOfWinNum));
            if (i == 2) {
                break;
            }
        }
    }
}
