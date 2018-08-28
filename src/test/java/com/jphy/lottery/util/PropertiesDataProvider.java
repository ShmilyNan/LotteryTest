package com.jphy.lottery.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * @author Lance
 * @Desription 从.properties文件中读取相关测试数据
 */
public class PropertiesDataProvider {

    public static String getTestData(String configFilePath, String key) {
        Configurations configs = new Configurations();
        Configuration config = null;
        try {
            config = configs.properties(new File(configFilePath));
//			config = new PropertiesConfiguration(configFilePath);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return String.valueOf(config.getProperty(key));
    }

    public static void writeProperties(String filePath, String key, String value) {
        SafeProperties sproperties = new SafeProperties();
        try {
            sproperties.load(new FileInputStream(filePath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        OutputStream output = null;
        try {
            output = new FileOutputStream(filePath);
            for (Enumeration<?> e = sproperties.propertyNames(); e.hasMoreElements(); ) {
                String s = (String) e.nextElement(); // 遍历所有元素
                if (s.equals(key)) {
                    /**如果键相同则覆盖*/
                    sproperties.setProperty(s, value);
                } else {
                    /**否则就原样写入*/
                    sproperties.setProperty(key, value);
                }
            }

            //	properties.setProperty(key,value);//保存键值对到内存
            sproperties.store(output, "Lance modify" + new Date().toString());// 保存键值对到文件中
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

