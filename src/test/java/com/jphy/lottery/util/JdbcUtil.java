package com.jphy.lottery.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

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
		/*
		connection = getConnection(); // 同样先要获取连接，即连接到数据库
		Map<String,String> map = null;
		try {
			statement = connection.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			//PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			map = new HashMap<String,String>();
			map.put("number",rs.getString("number"));
			while (rs.next()) { // 判断是否还有下一个数据
				// 根据字段名获取相应的值
				switch (type){
					case 1:
						String bet_Total_Amount = rs.getString("bet_Total_Amount");
						map.put("bet_Total_Amount",bet_Total_Amount);
						String win_Amount = rs.getString("win_Amount");
						String rebate = rs.getString("UPPER_POINTS");
						map.put("win_Amount",win_Amount);
						map.put("UPPER_POINTS",rebate);
						break;
					case 2:
						String balance = rs.getString("num");
						map.put("balance",balance);
						break;
					case 0:
						map.put("number",rs.getString("number"));
						break;
				}
			}
			rs.close();
			connection.close(); // 关闭数据库连接
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
		*/


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
