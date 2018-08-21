package com.jphy.lottery.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.jphy.lottery.plugins.ReadXml.Numbers;
import com.jphy.lottery.plugins.ReadXml.ReadXMLByDom4j;
import com.jphy.lottery.plugins.Sort.Arrange;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class JdbcUtil {
    public static Logger logger = Logger.getLogger(JdbcUtil.class.getName());
    public static Arrange arrange = null;
    public static List<Numbers> numbersList;
    public static List<String> data = null;
    public static List<String> numberOfPK10 = null;
    public static List<String> digit = null;
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
                    jdbc_url + jdbc_db + "?user=" + jdbc_username + "&password=" + jdbc_password + "&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&useSSL=false");// 创建数据连接
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
        numbersList = new ReadXMLByDom4j().getNumbers(new File("./src/test/resources/data/numberListOf11X5.xml"));
        arrange = new Arrange();
        data = new ArrayList<String>();
        connection = getConnection();
        String number;
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
            for (int i = 1; i <= 40; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                if (lottery_type == 0 || lottery_type == 4 || lottery_type == 5 || lottery_type == 6) {
                    for (int j = 0; j <= 99999; j++) {
                        // 构建SQL后缀
                        if (j < 10) {
                            number = "0000" + j;
                        } else if (j > 9 && j < 100) {
                            number = "000" + j;
                        } else if (j > 99 && j < 1000) {
                            number = "00" + j;
                        } else if (j > 999 && j < 10000) {
                            number = "0" + j;
                        } else {
                            number = String.valueOf(j);
                        }
                        suffix.append("('" + lottery_type + "','" + number + "'),");
                    }
                } else if (lottery_type >= 8 && lottery_type <= 10) {
                    for (int j = 1; j <= 6; j++) {
                        for (int k = 1; k <= 6; k++) {
                            for (int l = 1; l <= 6; l++) {
                                number = String.valueOf(j) + String.valueOf(k) + String.valueOf(l);
                                // 构建SQL后缀
                                suffix.append("('" + lottery_type + "','" + number + "'),");
                            }
                        }
                    }
                } else if (lottery_type == 11) {
                    for (int j = 0; j < numbersList.size(); j++) {
                        number = numbersList.get(j).getNumber();
                        // 构建SQL后缀
                        suffix.append("('" + lottery_type + "','" + number + "'),");
                        System.out.println(numbersList.get(j).getNumber());
                    }
                } else if (lottery_type == 1) {
                    if (i == 1) {
                        for (int j = 0; j < 10; j++) {
                            data.add(String.valueOf(j));
                        }
                        numberOfPK10 = arrange.arrangeSelect(data, new ArrayList<String>(), data.size());
                    }
                    for (int j = 90720 * (i - 1); j < 90720 * i; j++) {
                        number = numberOfPK10.get(j);
                        // 构建SQL后缀
                        suffix.append("('" + lottery_type + "','" + number + "'),");
                    }
                    System.out.println("准备第" + i + "次提交");
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
            // 关闭连接
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

    public static void updateResult(int lottery_type, List<String> number) throws Exception {
        connection = getConnection();
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        try {
            // 保存sql中缀
            StringBuffer infix = new StringBuffer();
            // 设置事务为非自动提交
            connection.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(" ");//准备执行语句
            //for (int i = 0; i < number.size(); i++) {
            //    ff(number.get(i), lottery_type);
            //    //infix = new StringBuffer();
            //    // 第j次提交步长
            //    if (lottery_type == 0 || (lottery_type >= 4 && lottery_type <= 6)) {
            //
            //        result = StringUtils.join(digit, ",");
            //        // 构建SQL后缀
            //        infix.append("when '" + number.get(i) + "' then '" + result + "' ");
            //    } else if (lottery_type >= 8 && lottery_type <= 10) {
            //        result = StringUtils.join(digit, ",");
            //        // 构建SQL后缀
            //        infix.append("when '" + number.get(i) + "' then '" + result + "' ");
            //    } else if (lottery_type == 11) {
            //        result = StringUtils.join(digit, ",");
            //        // 构建SQL后缀
            //        infix.append("when '" + number.get(i) + "' then '" + result + "' ");
            //    } else if (lottery_type == 1) {
            //        result = StringUtils.join(digit, ",");
            //        // 构建SQL后缀
            //        infix.append("when '" + number.get(i) + "' then '" + result + "' ");
            //    }
            //}

            for (int i = 0; i < number.size(); i++) {
                ff(number.get(i), lottery_type);
                if (lottery_type == 1) {
                    String result = StringUtils.join(digit, ",");
                    String sql = "update basic_number set RESULT = '" + result + "' WHERE LOTTERY_TYPE = " + lottery_type + " and number = '" + number.get(i) + "'";
                    pst.addBatch(sql);
                }
            }
            // 执行操作
            pst.executeBatch();
            // 提交事务
            connection.commit();
            // 清空上一次添加的数据
            infix = new StringBuffer();
            // 关闭连接
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

    public static List<String> queryNumbersToUpdateResult(int lotteryType, int orders) {
        connection = getConnection(); // 同样先要获取连接，即连接到数据库
        List<String> numbers = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            statement = (Statement) connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT n.number                       ")
                    .append(" FROM basic_number n, lottery_order_001 l ")
                    .append("WHERE n.LOTTERY_TYPE = l.LOTTERY_TYPE ")
                    .append("  AND n.LOTTERY_TYPE =               " + lotteryType)
                    .append("  AND n.RESULT IS NULL                ")
                    .append("GROUP BY n.number HAVING count(1) = " + orders);
            ResultSet rs = statement.executeQuery(sb.toString()); // 执行sql查询语句，返回查询数据的结果集
            while (rs.next()) { // 判断是否还有下一个数据
                // 根据字段名获取相应的值
                numbers.add(rs.getString("number"));
            }
            connection.commit();
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
            //ResultSet rs = statement.executeQuery("SELECT NUMBER FROM basic_number WHERE LOTTERY_TYPE = "+lotteryType
            //        +" and RESULT is NULL and NUMBER <= 30000 ORDER BY NUMBER ASC"); // 执行sql查询语句，返回查询数据的结果集
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

    public static void ff(String number, int lotteryType) {
        digit = new ArrayList<>();
        if (lotteryType == 1) {
            for (int i = 0; i < number.length(); i++) {
                if (number.substring(i, i + 1).equals("0"))
                    digit.add("1" + number.substring(i, i + 1));
                else
                    digit.add("0" + number.substring(i, i + 1));
            }
        } else if (lotteryType == 11) {
            for (int i = 0; i < number.length(); i = i + 2) {
                digit.add(number.substring(i, i + 2));
            }
        } else {
            for (int i = 0; i < number.length(); i++) {
                digit.add(number.substring(i, i + 1));
            }
        }
    }
}
