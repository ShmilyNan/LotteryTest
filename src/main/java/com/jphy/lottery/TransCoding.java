package com.jphy.lottery;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class TransCoding {
    public static void main(String[] args) throws UnsupportedEncodingException {
        List<String> list = new ArrayList<>();
        String deurl = URLDecoder.decode("content=%5B%7B%22betRange%22%3A+%22%E4%B8%80%E6%98%9F%22%2C%22playType%22%3A+%22%E5%A4%8D%E5%BC%8F%22%2C%22betContent%22%3A+%22%220+1+2+3+4+5+6+7+8+9%2C0+1+2+3+4+5+6+7+8+9%2C0+1+2+3+4+5+6+7+8+9%2C0+1+2+3+4+5+6+7+8+9%2C0+1+2+3+4+5+6+7+8+9%22%22%2C%22mutiple%22%3A+2%2C%22amount%22%3A+%221%22%2C%22continuity%22%3A+1%2C%22isWinStop%22%3A+0%7D%5D", "UTF-8");
        System.out.println(deurl);
        String str = "123456";
        System.out.println(str.length());
        for (int i = 0; i < str.length(); i = i + 2) {
            //if (i%2==0)
            list.add(str.substring(i, i + 2));
        }
        System.out.println(StringUtils.join(list, ","));

        for (int i = 0; i < str.length(); i++) {
            list.add("0" + str.substring(i, i + 1));
            System.out.println(str.substring(i, i + 1));
        }
        System.out.println(StringUtils.join(list, ","));
    }
}
