package com.jphy.lottery.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.bridj.cpp.std.list;

public class JdbcUtil {
    public static Logger logger = Logger.getLogger(JdbcUtil.class.getName());
    // 创建静态全局变量
    static Connection connection;
    static Statement statement;

    private static Connection getConnection() {
        String jdbc_url = null;
        String jdbc_db = null;
        String jdbc_driver = null;
        String jdbc_username = null;
        String jdbc_password = null;
        try {
            Properties prop = new Properties();
            // InputStream inStream =
            // JdbcUtil.class.getResourceAsStream("config/database.properties");
            InputStream inStream = new FileInputStream(new File("./src/test/resources/config/database.properties"));
            prop.load(inStream);
            jdbc_url = prop.getProperty("jdbc_url");
            jdbc_db = prop.getProperty("jdbc_db");
            jdbc_driver = prop.getProperty("jdbc_driver");
            jdbc_username = prop.getProperty("jdbc_username");
            jdbc_password = prop.getProperty("jdbc_password");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Connection connection = null; // 创建用于连接数据库的Connection对象
        try {
            Class.forName(jdbc_driver);// 加载Mysql数据驱动
            // connection = DriverManager.getConnection(jdbc_url + jdbc_db, jdbc_name,
            // jdbc_password);// 创建数据连接
            connection = DriverManager.getConnection(
                    jdbc_url + jdbc_db + "?user=" + jdbc_username + "&password=" + jdbc_password + "&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC");// 创建数据连接
            //connection = DriverManager.getConnection(jdbc_url,jdbc_username,jdbc_password);// 创建数据连接
        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return connection; // 返回所建立的数据库连接
    }

    /* 插入数据记录，并输出插入的数据记录数 */
    public static void insert(String sql) {
        connection = getConnection(); // 首先要获取连接，即连接到数据库
        try {
            logger.info("jdbc插入数据开始");

            statement = connection.createStatement(); // 创建用于执行静态sql语句的Statement对象
            int count = statement.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
            System.out.println("更新 " + count + " 条数据"); // 输出插入操作的处理结果
            statement.close();
            connection.close(); // 关闭数据库连接
            logger.info("jdbc插入数据结束");
        } catch (SQLException e) {
            System.out.println("插入数据失败" + e.getMessage());
        }
    }

    public static void insertNumbers(int lottery_type) {
        connection = getConnection();
        String number1;
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO basic_number (lottery_type,number) VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            connection.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(" ");//准备执行语句
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
                    //for (int j = 0; j <numbersList.size(); j++) {
                    //    number1 = numbersList.get(j).getNumber().replace(",","");
                    //    // 构建SQL后缀
                    //    suffix.append("('" + lottery_type + "','" + number1 + "'),");
                    //    System.out.println(numbersList.get(j).getNumber());
                    //}
                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                connection.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("Insert time : " + (end - begin) / 1000 + " s");
        System.out.println("Insert completion");
    }

    /* 更新符合要求的记录，并返回更新的记录数目 */
    public static void update(String sql) {
        connection = getConnection(); // 同样先要获取连接，即连接到数据库
        try {
            logger.info("jdbc更新数据开始");
            // String sql =
            // "update staff set wage='2200' where name = 'lucy'";// 更新数据的sql语句
            statement = connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            int count = statement.executeUpdate(sql);// 执行更新操作的sql语句，返回更新数据的个数
            System.out.println("更新 " + count + " 条数据"); // 输出更新操作的处理结果
            statement.close();
            connection.close(); // 关闭数据库连接
            logger.info("jdbc更新数据结束");
        } catch (SQLException e) {
            System.out.println("更新数据失败");
        }
    }

    public static void updateResult(int lottery_type, String number) throws Exception {
        connection = getConnection();
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "update basic_number SET RESULT = case number ";
        try {
            // 保存sql后缀
            StringBuffer infix = new StringBuffer();
            // 设置事务为非自动提交
            connection.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(" ");//准备执行语句
            String result = "";
            infix = new StringBuffer();
            // 第j次提交步长
            if (lottery_type == 0 || lottery_type == 4 || lottery_type == 5 || lottery_type == 6) {
                result = String.format("%s,%s,%s,%s,%s", number.substring(0, 1), number.substring(1, 2), number.substring(2, 3), number.substring(3, 4), number.substring(4));
                // 构建SQL后缀
                infix.append("when '" + number + "' then '" + result + "' ");
            } else if (lottery_type >= 8 && lottery_type <= 10) {
                result = number.substring(0, 1) + "," + number.substring(1, 2) + "," + number.substring(2);
                // 构建SQL后缀
                infix.append("when '" + number + "' then '" + result + "' ");
            }
            String suffix = " end where lottery_type = " + lottery_type + " and  result is null  ";
            // 构建完整SQL
            String sql = prefix + infix.substring(0, infix.length() - 1) + suffix;
            // 添加执行SQL
            pst.addBatch(sql);
            // 执行操作
            pst.executeBatch();
            // 提交事务
            connection.commit();
            // 清空上一次添加的数据
            infix = new StringBuffer();
            //Thread.sleep(90000);
            // 头等连接
            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("Update time : " + (end - begin) / 1000 + " s");
        System.out.println("Update completion");
    }

/*
    /**
     *
     * @param lottery_type
     * @param endNumber
     * @throws Exception
     */
    /*
    public static void updateResult(int lottery_type, int endNumber) throws Exception {
        connection = getConnection();
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "update basic_number SET RESULT = case number ";
        try {
            // 保存sql后缀
            StringBuffer infix = new StringBuffer();
            // 设置事务为非自动提交
            connection.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(" ");//准备执行语句
            String result = "";
            String numbers = "";
            infix = new StringBuffer();
            // 第j次提交步长
            if (lottery_type == 0 || lottery_type == 4 || lottery_type == 5 || lottery_type == 6) {
                for (int n = endNumber; n <= endNumber; n++) {
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
            connection.commit();
            // 清空上一次添加的数据
            infix = new StringBuffer();
            //Thread.sleep(90000);
            // 头等连接
            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("Update time : " + (end - begin) / 1000 + " s");
        System.out.println("Update completion");
    }
    */

    /* 查询数据库，输出符合要求的记录的情况 */
    public static String query(String sql, String fieldName) {
        connection = getConnection(); // 同样先要获取连接，即连接到数据库
        String fieldValue = null;
        try {
            statement = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            ResultSet rs = statement.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
            while (rs.next()) { // 判断是否还有下一个数据
                // 根据字段名获取相应的值
                fieldValue = rs.getString(fieldName);
            }
            rs.close();
            statement.close();
            connection.close(); // 关闭数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fieldValue;
    }

    public static List<String> queryNumbersToUpdateResult(int lotteryType,int orders) {
        connection = getConnection(); // 同样先要获取连接，即连接到数据库
        List<String> numbers = new ArrayList<>();

        try {
            statement = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            ResultSet rs = statement.executeQuery("SELECT number FROM basic_number WHERE LOTTERY_TYPE = "+lotteryType+" AND RESULT is NULL AND NUMBER in(SELECT number FROM lottery_order_001 WHERE LOTTERY_TYPE = "+lotteryType+" GROUP BY number HAVING count(*) = "+orders+");"); // 执行sql查询语句，返回查询数据的结果集
            while (rs.next()) { // 判断是否还有下一个数据
                // 根据字段名获取相应的值
                numbers.add(rs.getString("number"));
            }
            rs.close();
            statement.close();
            connection.close(); // 关闭数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return numbers;
    }

    public static List<String> queryNumbers(int lotteryType) {
        connection = getConnection(); // 同样先要获取连接，即连接到数据库
        List<String> numbers = new ArrayList<>();

        try {
            statement = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            ResultSet rs = statement.executeQuery("SELECT number FROM basic_number WHERE LOTTERY_TYPE = " + lotteryType
                    + " order by id asc"); // 执行sql查询语句，返回查询数据的结果集
            while (rs.next()) { // 判断是否还有下一个数据
                // 根据字段名获取相应的值
                numbers.add(rs.getString("number"));
            }
            rs.close();
            statement.close();
            connection.close(); // 关闭数据库连接
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return numbers;
    }

    /* 删除符合要求的记录，输出情况 */
    public static void delete(String sql) {
        connection = getConnection(); // 同样先要获取连接，即连接到数据库
        try {
            logger.info("jdbc删除数据开始");
            // String sql = "delete from staff  where name = 'lili'";//
            // 删除数据的sql语句
            statement = connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            int count = statement.executeUpdate(sql);// 执行sql删除语句，返回删除数据的数量
            System.out.println("agent表中删除了【 " + count + " 】条数据。"); // 输出删除操作的处理结果
            statement.close();
            connection.close(); // 关闭数据库连接
            logger.info("jdbc删除数据结束");
        } catch (SQLException e) {
            System.out.println("删除数据失败");
        }
    }
}
