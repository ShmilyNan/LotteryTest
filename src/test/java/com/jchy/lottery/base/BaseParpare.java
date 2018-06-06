package com.jchy.lottery.base;

/**
 * @author Lance
 * @Description 测试开始 和 测试结束 的操作
 * 
 * */
import java.io.IOException;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import com.jchy.lottery.util.ExcelDataProvider;
import com.jchy.lottery.util.SeleniumUtil;

public class BaseParpare {
	static Logger logger = Logger.getLogger(BaseParpare.class.getName());
	protected SeleniumUtil seleniumUtil = null;
	// 添加成员变量来获取beforeClass传入的context参数
	protected ITestContext testContext = null;

	@BeforeClass
	/**启动浏览器并打开测试页面*/
	public void startTest(ITestContext context) {
		seleniumUtil = new SeleniumUtil();
		// 这里得到了context值
		this.testContext = context;
		String browserName = context.getCurrentXmlTest().getParameter("browserName");

		try {
			seleniumUtil.launchBrowser(browserName, context);
		} catch (Exception e) {
			//seleniumUtil.quit();
			logger.error("浏览器不能正常工作，请检查是不是被手动关闭或者其他原因",e);
			TestCase.fail("浏览器不能正常工作，请检查是不是被手动关闭或者其他原因");
		}
		// 设置一个testng上下文属性，将driver存起来，之后可以使用context随时取到
		testContext.setAttribute("SELENIUM_DRIVER", seleniumUtil.driver);
	}

	@AfterClass
	/**结束测试关闭浏览器*/
	public void endTest() {
		if (seleniumUtil.driver != null) {
			seleniumUtil.quit();
		} else {
			logger.error("浏览器driver没有获得对象,退出操作失败");
			TestCase.fail("浏览器driver没有获得对象,退出操作失败");
		}
	}

	
	/**
	 * 测试数据提供者 - 方法
	 * */
	@DataProvider(name = "data")
	public Iterator<Object[]> dataFortestMethod() throws IOException {
		String moduleName = null; // 模块的名字
		String caseNum = null; // 用例编号
		String className = this.getClass().getName();
		int dotIndexNum = className.indexOf("."); // 取得第一个.的index
		int underlineIndexNum = className.indexOf("_"); // 取得第一个_的index

		if (dotIndexNum > 0) {
			moduleName = className.substring(30, className.lastIndexOf(".")); // 取到模块的名称
		}

		if (underlineIndexNum > 0) {
			caseNum = className.substring(underlineIndexNum + 1, underlineIndexNum + 4); // 取到用例编号
		}
		// 将模块名称和用例的编号传给 ExcelDataProvider ，然后进行读取excel数据
		return new ExcelDataProvider(moduleName, caseNum);
	}
}
