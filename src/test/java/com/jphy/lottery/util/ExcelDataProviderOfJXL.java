package com.jphy.lottery.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.testng.Assert;

/**
 * @author Lance
 * @description: 读取Excel数据
 *               说明：
 *               Excel放在Data文件夹下
 *               Excel命名方式：测试类名.xlsx
 *               Excel的sheet命名方式：测试方法名
 *               Excel第一行为Map键值
 */
public class ExcelDataProviderOfJXL implements Iterator<Object[]> {

	private Workbook book = null;
	private Sheet sheet = null;
	private int rowNum = 0;
	private int currentRowNo = 0;
	private int columnNum = 0;
	private String[] columnnName;
	private String path = null;
	public static Logger logger = Logger.getLogger(ExcelDataProviderOfJXL.class.getName());

	/*
	 * @description 
	 * 2个参数：
	 * moduleName - 模块的名称
	 * caseNum - 测试用例编号
	 **/
	public ExcelDataProviderOfJXL(String moduleName, String caseNum) {

		try {
			path = "./src/test/resources/data/" + moduleName + ".xlsx";
			InputStream inputStream = new FileInputStream(path);

			book = Workbook.getWorkbook(inputStream);
			// sheet = book.getSheet(methodname);
			sheet = book.getSheet(caseNum); // 读取第一个sheet
			rowNum = sheet.getRows(); // 获得该sheet的 所有行
			Cell[] cell = sheet.getRow(0);// 获得第一行的所有单元格
			columnNum = cell.length; // 单元格的个数 值 赋给 列数
			columnnName = new String[cell.length];// 开辟 列名的大小

			for (int i = 0; i < cell.length; i++) {
				columnnName[i] = cell[i].getContents().toString(); // 第一行的值
			}
			this.currentRowNo++;

		} catch (FileNotFoundException e) {
			logger.error("Not found the file:" + "[" + path + "]");
			Assert.fail("Not found the file:" + "[" + path + "]");
		} catch (Exception e) {
			logger.error("Unable to read [" + path + "]");
			Assert.fail("Unable to read [" + path + "]");
		}
	}

	public boolean hasNext() {

		if (this.rowNum == 0 || this.currentRowNo >= this.rowNum) {
			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else {
			// sheet下一行内容为空判定结束
			if ((sheet.getRow(currentRowNo))[0].getContents().equals(""))
				return false;
			return true;
		}
	}

	public Object[] next() {

		Cell[] c = sheet.getRow(this.currentRowNo);

		Map<String, String> data = new HashMap<String, String>();
		// List<String> list = new ArrayList<String>();

		for (int i = 0; i < this.columnNum; i++) {

			String temp = "";

			try {
				temp = c[i].getContents().toString();
			} catch (ArrayIndexOutOfBoundsException ex) {
				temp = "";
			}

			// if(temp != null&& !temp.equals(""))
			// list.add(temp);
			data.put(this.columnnName[i], temp);
		}
		Object object[] = new Object[1];
		object[0] = data;
		this.currentRowNo++;
		return object;
	}

	public void remove() {
		throw new UnsupportedOperationException("remove unsupported.");
	}
}
