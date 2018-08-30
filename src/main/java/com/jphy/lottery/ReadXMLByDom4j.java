package com.jphy.lottery;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 用DOM4J方法读取xml文件
 *
 * @author Lance
 */
public class ReadXMLByDom4j {

    private List<Numbers> numbersList = null;
    private Numbers numbers = null;

    public List<Numbers> getNumbers(File file) {
        // 解析TestDatas.xml文件
        //创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载TestDatas.xml文件,获取docuemnt对象。
            Document document = reader.read(file);
            // 通过document对象获取根节点betOrderStore
            Element numberStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator storeIt = numberStore.elementIterator();

            numbersList = new ArrayList();
            while (storeIt.hasNext()) {
                numbers = new Numbers();
                Element numberElement = (Element) storeIt.next();
                //遍历numberElement的属性名和属性值
                List<Attribute> attributes = numberElement.attributes();
                for (Attribute attribute : attributes) {
                    //if(attribute.getName().equals("orderId")){
                    //    String orderId = attribute.getValue();//System.out.println(orderId);
                    //    betOrder.setOrderId(Integer.parseInt(orderId));
                    //}
                    System.out.println("属性名：" + attribute.getName() + "--属性值：" + attribute.getValue());
                }

                Iterator numberIt = numberElement.elementIterator();
                while (numberIt.hasNext()) {
                    Element child = (Element) numberIt.next();
                    String nodeName = child.getName();
                    if (nodeName.equals("Result")) {
                        String number = child.getStringValue();
                        numbers.setNumber(number);
                    } else if (nodeName.equals("SumOfWinNum")) {
                        String sumOfWinNum = child.getStringValue();
                        numbers.setSumOfWinNum(sumOfWinNum);
                    }
                }
                numbersList.add(numbers);
                numbers = null;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return numbersList;
    }

}
