package com.lottery.apitest;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lottery.apitest.BetOrder;

/**
 * 用DOM4J方法读取xml文件
 * 
 * @author Lance
 */
public class ReadXMLByDom4j {

	private List<BetOrder> betOrderList = null;
	private BetOrder betOrder = null;

	public List<BetOrder> getBetOrders(File file){
          
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element betOrderstore = document.getRootElement();
            Iterator storeit = betOrderstore.elementIterator();
              
            betOrderList = new ArrayList<BetOrder>();
            while(storeit.hasNext()){
                
                betOrder = new BetOrder();
                Element betOrderElement = (Element)storeit.next();
                //遍历betOrderElement的属性
                List<Attribute> attributes = betOrderElement.attributes();
                for(Attribute attribute : attributes){
                    if(attribute.getName().equals("orderId")){
                        String orderId = attribute.getValue();//System.out.println(orderId);
                        betOrder.setOrderId(Integer.parseInt(orderId));
                    }
                }
                  
                Iterator betOrderIt = betOrderElement.elementIterator();
                while(betOrderIt.hasNext()){
                    Element child = (Element) betOrderIt.next();
                    String nodeName = child.getName();
                    if(nodeName.equals("betRange")){
                        //System.out.println(child.getStringValue());
                        String name = child.getStringValue();
                        betOrder.setBetRange(name);
                    }else if(nodeName.equals("playType")){
                        String playType = child.getStringValue();
                        betOrder.setPlayType(playType);
                    }else if(nodeName.equals("betContent")){
                        String betContent = child.getStringValue();
                        betOrder.setBetContent(betContent);
                    }else if(nodeName.equals("mutiple")){
                        String mutiple = child.getStringValue();
                        betOrder.setMutiple(Integer.parseInt(mutiple));
                    }else if(nodeName.equals("amount")){
                    	String amount = child.getStringValue();
                    	betOrder.setAmount(Integer.parseInt(amount));
                    }else if(nodeName.equals("spend")){
                    	String spend = child.getStringValue();
                    	betOrder.setSpend(Integer.parseInt(spend));
                    }else if(nodeName.equals("spend")){
                    	String drawnAmount = child.getStringValue();
                    	betOrder.setDrawnAmount(Double.parseDouble(drawnAmount));
                    }else if(nodeName.equals("balance")){
                    	String balance = child.getStringValue();
                    	betOrder.setBalance(Double.parseDouble(balance));
                    }
                }
                betOrderList.add(betOrder);
                betOrder = null;
                  
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return betOrderList;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("src/res/BetDatas.xml");
		List<BetOrder> betOrderList = new ReadXMLByDom4j().getBetOrders(file);
		for (BetOrder betOrder : betOrderList) {
			System.out.println(betOrder);
		}
	}
}
