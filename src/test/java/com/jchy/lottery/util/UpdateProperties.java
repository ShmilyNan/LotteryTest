package com.jchy.lottery.util;

import java.io.*;
import java.text.MessageFormat;
import java.util.Properties;

public class UpdateProperties {
    private static SafeProperties sprop;

    public static void load(String path){
        //这里的path是项目文件的绝对路径
        //先获取项目绝对路径：Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //然后在项目路径后面拼接"properties/sysConfig.properties";
        sprop= new SafeProperties();// 属性集合对象
        FileInputStream fis;
        try {
            System.out.println(path);
            fis = new FileInputStream(path);
            sprop.load(fis);
            fis.close();// 关闭流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //参数为要修改的文件路径  以及要修改的属性名和属性值
    public static Boolean updatePro(String path,String key,String value){
        if(sprop==null){
            load(path);
            System.out.println("修改前重新加载一遍");
        }
        System.out.println("获取添加或修改前的属性值："+key+"=" + sprop.getProperty(key));
        sprop.setProperty(key, value);

        // 文件输出流
        try {
            FileOutputStream fos = new FileOutputStream(path);
            // 将Properties集合保存到流中
            sprop.store(fos, "Copyright (c) Boxcode Studio");
            fos.close();// 关闭流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        System.out.println("获取添加或修改后的属性值："+key+"=" + sprop.getProperty(key));
        return true;
    }

    //参数为要修改的文件路径  以及要修改的属性名和属性值
    public static String getPro(String path,String key){
        if(sprop==null){
            load(path);
            System.out.println("重新加载一遍");
        }
        FileInputStream fis;
        try {
            fis = new FileInputStream(path);
            sprop.load(fis);// 将属性文件流装载到Properties对象中
            fis.close();// 关闭流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("查询到的"+key+"的值："+sprop.getProperty(key));
        return sprop.getProperty(key);
    }

    public static void updateProperties(String key,String value) throws IOException {

        Properties prop = new Properties();
        InputStream in = UpdateProperties.class.getClassLoader().getResourceAsStream(
                "interfaceinfo.properties");

        prop.load(in);
        //参数
        String params=value;
        //从配置文件中读取模板字符串替换
        String msg=MessageFormat.format(prop.getProperty(key),params);
        System.out.println("模板字符串："+prop);
        System.out.println("替换后的字符串："+msg);
    }
}
