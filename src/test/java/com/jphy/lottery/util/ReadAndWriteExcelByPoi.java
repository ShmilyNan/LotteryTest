package com.jphy.lottery.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;

public class ReadAndWriteExcelByPoi {

    private static final Logger logger = Logger.getLogger(ReadAndWriteExcelByPoi.class.getName());
    public static double sumOfWinNum = 0;

    public static double getSumOfWinNum(String path, int rowNum, int column) {
        try {
            // 创建 Excel 文件的输入流对象
            FileInputStream excelFileInputStream = new FileInputStream(path);
            // XSSFWorkbook 就代表一个 Excel 文件
            // 创建其对象，就打开这个 Excel 文件
            XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
            excelFileInputStream.close();
            //按索引获取sheet，索引从0开始
            XSSFSheet sheet = workbook.getSheetAt(1);
            //循环读取表格内容
            for (int rowIndex = 1; rowIndex <= rowNum; rowIndex++) {
                // XSSFRow 代表一行数据
                XSSFRow r = sheet.getRow(rowIndex);
                if (r == null) {
                    continue;
                }
                //XSSFCell resultNumberCell = r.getCell(column); // 开奖号码列
                //XSSFCell sumOfWinNumberCell = r.getCell(column+1); // 中奖金额之和
                XSSFCell winNumberCell = r.getCell(column); // 每单中奖金额
                System.out.println(winNumberCell.getNumericCellValue());
                sumOfWinNum = sumOfWinNum + winNumberCell.getNumericCellValue();
                //StringBuilder employeeInfoBuilder = new StringBuilder();
                //employeeInfoBuilder.append("订单中奖信息 --> ")
                //        .append("开奖号码 : ").append(resultNumberCell.getStringCellValue())
                //        //.append(" , 中奖金额总和 : ").append(sumOfWinNumberCell.getNumericCellValue())
                //        .append(" , 每注中奖金额 : ").append(winNumberCell.getNumericCellValue());
                //System.out.println("第" + rowIndex + "行" + employeeInfoBuilder.toString());
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return sumOfWinNum;
    }

    public static void updateExcel(String filePath, int row, int column, String value) {
        try {
            // 创建 Excel 文件的输入流对象
            FileInputStream fileInputStream = new FileInputStream(filePath);
            // XSSFWorkbook 就代表一个 Excel 文件
            // 创建其对象，就打开这个 Excel 文件
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            // 打开这个 sheet
            XSSFSheet sheet = workbook.getSheetAt(1);
            //获取纵坐标
            XSSFRow r = sheet.getRow(row);
            //获取横坐标，定位到指定单元格
            XSSFCell cell = r.getCell(column);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            //修改前单元格的值
            String str = cell.getStringCellValue();
            cell.setCellValue(value);
            //关闭excel前先刷新所有单元格的公式

            fileInputStream.close();//关闭文件输入流
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            for (int rowIndex = 1; rowIndex <= 132; rowIndex++) {
                updateFormula(workbook,sheet,rowIndex);
            }
            fileOutputStream.close();//关闭文件输出流
            System.out.println("单元格值由:" + str + "更新为:" + value + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateFormula(Workbook wb,Sheet s,int row) {
        Row r = s.getRow(row);
        Cell c = null;
        FormulaEvaluator eval = null;
        if (wb instanceof HSSFWorkbook) {
            eval = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
            //eval.evaluateAll();
        } else if (wb instanceof XSSFWorkbook) {
            eval = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
            //eval.evaluateAll();
        }

        for (int i = r.getFirstCellNum(); i < r.getLastCellNum(); i++) {
            try {
                c = r.getCell(i);
                if (c==null){
                    continue;
                }
                if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    eval.evaluateFormulaCell(c);
                }
            }catch (Exception e){
                System.out.println("第"+row+"行，第"+i+"列");
                e.printStackTrace();
            }
        }
    }
}
