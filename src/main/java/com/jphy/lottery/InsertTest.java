package com.jphy.lottery;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class InsertTest {
    public static List<Numbers> numbersList;
    public static int lottery_type = 8;

    public static void main(String[] args) throws Exception {
        numbersList = new ReadXMLByDom4j().getNumbers(new File("./src/main/resources/X115_Numbers.xml"));
        final String url = "jdbc:mysql://192.168.1.129:3306/lottery_v2_2?autoReconnect=true&rewriteBatchedStatements=true&useUnicode=true&zeroDateTimeBehavior=convertToNull";
        final String name = "com.mysql.cj.jdbc.Driver";
        final String user = "root";
        final String password = "whcd@dev";
        Connection conn = null;
        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("Get connection success！");
            //update(conn);
            insertNumbers(conn);
        } else {
            System.out.println("Get Connection Failure！");
        }
    }

    public static void insertNumbers(Connection conn) {

        String number1;
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO basic_number (lottery_type,number) VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 1; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                if (lottery_type == 0 || lottery_type == 4 || lottery_type == 5 || lottery_type == 6) {
                    for (int j = 0; j <= 99999; j++) {
                        // 构建SQL后缀
                        if (j < 10) {
                            number1 = "0000" + j;
                        } else if (j > 9 && j < 100) {
                            number1 = "000" + j;
                        } else if (j > 99 && j < 1000) {
                            number1 = "00" + j;
                        } else if (j > 999 && j < 10000) {
                            number1 = "0" + j;
                        } else {
                            number1 = String.valueOf(j);
                        }
                        suffix.append("('" + lottery_type + "','" + number1 + "'),");
                    }
                } else if (lottery_type >= 8 && lottery_type <= 10) {
                    for (int j = 1; j <= 6; j++) {
                        for (int k = 1; k <= 6; k++) {
                            for (int l = 1; l <= 6; l++) {
                                number1 = String.valueOf(j) + String.valueOf(k) + String.valueOf(l);
                                // 构建SQL后缀
                                suffix.append("('" + lottery_type + "','" + number1 + "'),");
                            }
                        }
                    }
                } else if (lottery_type == 11) {
                    for (int j = 0; j < numbersList.size(); j++) {
                        number1 = numbersList.get(j).getNumber().replace(",", "");
                        // 构建SQL后缀
                        suffix.append("('" + lottery_type + "','" + number1 + "'),");
                        System.out.println(numbersList.get(j).getNumber());
                    }
                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                //pst.addBatch(sql);
                // 执行操作
                //pst.executeBatch();
                // 提交事务
                //conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("Insert time : " + (end - begin) / 1000 + " s");
        System.out.println("Insert completion");
    }

    public static void update(Connection conn) throws Exception {
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "update basic_number SET RESULT = case number ";
        try {
            // 保存sql后缀
            StringBuffer infix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
            String result = "";
            String numbers = "";
            infix = new StringBuffer();
            // 第j次提交步长
            if (lottery_type == 0 || lottery_type == 4 || lottery_type == 5 || lottery_type == 6) {
                for (int n = 1290; n <= 5000; n++) {
                    if (n >= 0 && n < 10) {
                        result = String.format("0,0,0,0,%s", String.valueOf(n));
                        numbers = "0000" + n;
                        // 构建SQL后缀
                        infix.append("when '" + numbers + "' then '" + result + "' ");
                    } else if (n > 9 && n < 100) {
                        result = String.format("0,0,0,%s,%s", String.valueOf(n).substring(0, 1), String.valueOf(n).substring(1));
                        numbers = "000" + n;
                        // 构建SQL后缀
                        infix.append("when '" + numbers + "' then '" + result + "' ");
                    } else if (n > 99 && n < 1000) {
                        result = String.format("0,0,%s,%s,%s", String.valueOf(n).substring(0, 1), String.valueOf(n).substring(1, 2), String.valueOf(n).substring(2));
                        numbers = "00" + n;
                        // 构建SQL后缀
                        infix.append("when '" + numbers + "' then '" + result + "' ");
                    } else if (n > 999 && n < 10000) {
                        result = String.format("0,%s,%s,%s,%s", String.valueOf(n).substring(0, 1), String.valueOf(n).substring(1, 2), String.valueOf(n).substring(2, 3), String.valueOf(n).substring(3));
                        numbers = "0" + n;
                        // 构建SQL后缀
                        infix.append("when '" + numbers + "' then '" + result + "' ");
                    } else {
                        result = String.format("%s,%s,%s,%s,%s", String.valueOf(n).substring(0, 1), String.valueOf(n).substring(1, 2), String.valueOf(n).substring(2, 3), String.valueOf(n).substring(3, 4), String.valueOf(n).substring(4));
                        // 构建SQL后缀
                        infix.append("when '" + n + "' then '" + result + "' ");
                    }
                }
            } else if (lottery_type >= 8 && lottery_type <= 10) {
                for (int j = 1; j <= 6; j++) {
                    for (int k = 1; k <= 6; k++) {
                        for (int l = 1; l <= 6; l++) {
                            numbers = String.valueOf(j) + String.valueOf(k) + String.valueOf(l);
                            result = j + "," + k + "," + l;
                            // 构建SQL后缀
                            infix.append("when '" + numbers + "' then '" + result + "' ");
                        }
                    }
                }
            }
            String suffix = " end where lottery_type = " + lottery_type + " and  result is null  ";
            // 构建完整SQL
            String sql = prefix + infix.substring(0, infix.length() - 1) + suffix;
            // 添加执行SQL
            pst.addBatch(sql);
            // 执行操作
            pst.executeBatch();
            // 提交事务
            conn.commit();
            // 清空上一次添加的数据
            infix = new StringBuffer();
            //Thread.sleep(90000);
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("Update time : " + (end - begin) / 1000 + " s");
        System.out.println("Update completion");
    }
}
