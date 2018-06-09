package com.jphy.lottery.util;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jphy.lottery.plugins.arrow.TestResultListener;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * @author Lance
 * @Description 包装所有selenium的操作以及通用方法，简化用例中代码量
 * */
public class SeleniumUtil {
	/** 使用Log4j，第一步就是获取日志记录器，这个记录器将负责控制日志信息 */
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public WebDriver driver = null;
	public WebDriver window = null;

	/***
	 * 启动浏览器并打开页面
	 * */
	public void launchBrowser(String browserName, ITestContext context) {

		String webUrl = context.getCurrentXmlTest().getParameter("testurl");
		int timeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		SelectBrowser select = new SelectBrowser();
		driver = select.selectExplorerByName(browserName, context);
		try {
			maxWindow(browserName);
			hasLoadPageSucceeded(timeOut);
			get(webUrl);
			logger.info("打开测试页面:[" + webUrl + "]");

		} catch (Exception e) {
			refresh();
			logger.info("页面刷新成功！");
		}

	}

	/**
	 * 最大化浏览器操作
	 * */
	public void maxWindow(String browserName) {
		logger.info("最大化浏览器:" + browserName);
		driver.manage().window().maximize();
	}

	/**
	 * 设定浏览器窗口大小： 设置浏览器窗口的大小有下面两个比较常见的用途：<br>
	 * 1、在统一的浏览器大小下运行用例，可以比较容易的跟一些基于图像比对的工具进行结合
	 * ，提升测试的灵活性及普遍适用性。比如可以跟sikuli结合，使用sikuli操作flash；<br>
	 * 2、在不同的浏览器大小下访问测试站点，对测试页面截图并保存，然后观察或使用图像比对工具对被测页面的前端样式进行评测。
	 * 比如可以将浏览器设置成移动端大小(320x480)，然后访问移动站点，对其样式进行评估；<br>
	 * */
	public void setBrowserSize(int width, int height) {
		driver.manage().window().setSize(new Dimension(width, height));
	}

	/**
	 * 包装查找元素的方法 element
	 * */
	public WebElement findElementBy(By by) {
		return driver.findElement(by);
	}

	/**
	 * 包装查找元素的方法 elements
	 * */
	public List<WebElement> findElementsBy(By by) {
		return driver.findElements(by);
	}

	/**
	 * 包装点击操作
	 * */
	public void click(WebElement element) {

		try {
			clickTheClickable(element, System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			logger.error("The element you clicked:[" + getLocatorByElement(element, ">") + "] is no longer exist!");
			Assert.fail("The element you clicked:[" + getLocatorByElement(element, ">") + "] is no longer exist!");
		} catch (Exception e) {
			logger.error("Failed to click element [" + getLocatorByElement(element, ">") + "]");
			Assert.fail("Failed to click element [" + getLocatorByElement(element, ">") + "]");
		}
		logger.info("点击元素 [" + getLocatorByElement(element, ">") + "]");
	}

	/** 不能点击时候重试点击操作 */
	public void clickTheClickable(WebElement element, long startTime, int timeOut) throws Exception {
		try {
			element.click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.warn(getLocatorByElement(element, ">") + " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				logger.warn(getLocatorByElement(element, ">") + " is unclickable, try again");
				clickTheClickable(element, startTime, timeOut);
			}
		}
	}

	/**
	 * 获得页面的标题
	 * */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * 获得元素的文本
	 * */
	public String getText(By elementLocator) {
		return driver.findElement(elementLocator).getText().trim();
	}

	/**
	 * 获得元素 属性的文本
	 * */
	public String getAttributeText(By elementLocator, String attribute) {
		return driver.findElement(elementLocator).getAttribute(attribute).trim();
	}

	/**
	 * 包装清除操作
	 * */
	public void clear(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			logger.error("Failed to clear [" + getLocatorByElement(element, ">") + "] contents!");
		}
		logger.info("清除元素 [" + getLocatorByElement(element, ">") + "]上的内容");
	}

	/**
	 * 向输入框输入内容
	 * */
	public void type(WebElement element, String key) {
		try {
			element.sendKeys(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to type the [" + key + "] to [" + getLocatorByElement(element, ">") + "]");
			Assert.fail("Failed to type the [" + key + "] to [" + getLocatorByElement(element, ">") + "]");
		}
		logger.info("输入：[" + key + "] 到 [" + getLocatorByElement(element, ">") + "]");
	}

	/**
	 * 模拟键盘操作的,比如Ctrl+A,Ctrl+C等 参数详解：<br>
	 * 1、WebElement element - 要被操作的元素 <br>
	 * 2、Keys key- 键盘上的功能键 比如ctrl ,alt等 <br>
	 * 3、String keyword - 键盘上的字母
	 * */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {

		element.sendKeys(Keys.chord(key, keyword));
	}

	/**
	 * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	 * */
	public void waitForElementToLoad(int timeOut, final By By) {
		logger.info("开始查找元素[" + By + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			logger.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");

		}
		logger.info("找到了元素 [" + By + "]");
	}

	/**
	 * 当jquery正在读取或处理比较大的json文件的时候，怎样判断它的读取活动已经完成
	 */

	public void waitForAjaxDone(int timeOut) {
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					JavascriptExecutor js = (JavascriptExecutor) d;
					return (Boolean) js.executeScript("return jQuery.active == 0"); // Jquery.active=0表示ajax活动已经完成
				}
			});
		} catch (TimeoutException e) {
			logger.error("Time out!! " + timeOut + " seconds passed and the jquery satate ：[!=0]");
			new TestResultListener().onTestFailure(it);
			quit();

		}

	}

	/**
	 * 判断文本是不是和需求要求的文本一致
	 * **/
	public void isTextCorrect(String actual, String expected) {

		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
		}
		logger.info("找到了期望的文字: [" + expected + "]");
	}

	/**
	 * 判断数值是否与期望的数值一致
	 * **/
	public void isNumCorrect(double actual, double expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
		}
		logger.info("找到了期望的文字: [" + expected + "]");
	}

	/**
	 * 判断编辑框是不是可编辑
	 * */
	public void isInputEdit(WebElement element) {

	}

	/**
	 * 等待alert出现
	 * */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 200;
		NoAlertPresentException lastException = null;

		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;

		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {

			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				lastException = e;
			}
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {
				break;
			}
		}
		throw lastException;
	}

	/**
	 * 暂停当前用例的执行，暂停的时间为：sleepTime
	 * */
	public void pause(int sleepTime) {
		if (sleepTime <= 0) {
			return;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出
	 * */
	public void quit() {
		driver.quit();
	}

	/**
	 * 切换frame - 根据String类型（frame名字）
	 * */
	public void inFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}

	/**
	 * 切换frame - 根据frame在当前页面中的顺序来定位
	 * */
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}

	/**
	 * 切换frame - 根据页面元素定位
	 * */
	public void switchFrame(WebElement element) {
		try {
			logger.info("Start switching to frame [" + getLocatorByElement(element, ">") + "]");
			driver.switchTo().frame(element);
		} catch (Exception e) {
			logger.info("Switch to frame [" + getLocatorByElement(element, ">") + "] failed");
			Assert.fail("Switch to frame [" + getLocatorByElement(element, ">") + "] failed");
		}
		logger.info("Switch to frame [" + getLocatorByElement(element, ">") + "] successed");
	}

	/**
	 * 选择下拉选项 -根据value
	 * */
	public void selectByValue(By by, String value) {
		Select s = new Select(driver.findElement(by));
		s.selectByValue(value);
	}

	/**
	 * 选择下拉选项 -根据index角标
	 * */
	public void selectByIndex(By by, int index) {
		Select s = new Select(driver.findElement(by));
		s.selectByIndex(index);
	}

	/** 检查checkbox是不是勾选 */
	public boolean doesCheckboxSelected(By elementLocator) {
		if (findElementBy(elementLocator).isSelected() == true) {
			logger.info("CheckBox: " + getLocatorByElement(findElementBy(elementLocator), ">") + " 被勾选");
			return true;
		} else
			logger.info("CheckBox: " + getLocatorByElement(findElementBy(elementLocator), ">") + " 没有被勾选");
		return false;

	}

	/**
	 * 选择下拉选项 -根据文本内容
	 * */
	public void selectByText(By by, String text) {
		Select s = new Select(driver.findElement(by));
		s.selectByVisibleText(text);
	}
	
	/**
	 * 获得当前select选择的值
	 * */
	public List<WebElement> getCurrentSelectValue(By by){
		List<WebElement> options = null;
		Select s = new Select(driver.findElement(by));
			options =  s.getAllSelectedOptions();
			return options;
	}

	/**
	 * 执行JavaScript 方法
	 * */
	public void executeJS(String js) {
		((JavascriptExecutor) driver).executeScript(js);
		logger.info("执行JavaScript语句：[" + js + "]");
	}

	/**
	 * 执行JavaScript 方法和对象
	 * */
	public void executeJS(String js, Object... args) {
		((JavascriptExecutor) driver).executeScript(js, args);
		logger.info("执行JavaScript语句：[" + js + "]");
	}

	/**
	 * get方法包装
	 * */
	public void get(String url) {
		driver.get(url);
	}

	/**
	 * close方法包装
	 * */
	public void close() {
		driver.close();
	}

	/**
	 * 刷新方法包装
	 * */
	public void refresh() {
		driver.navigate().refresh();
	}

	/**
	 * 后退方法包装
	 * */
	public void back() {
		driver.navigate().back();
	}

	/**
	 * 前进方法包装
	 * */
	public void forward() {
		driver.navigate().forward();
	}

	/**
	 * 包装selenium模拟鼠标操作 - 鼠标移动到指定元素
	 * */
	public void mouseMoveToElement(By by) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(driver.findElement(by));
		mouse.perform();
	}

	/**
	 * 包装selenium模拟鼠标操作 - 鼠标移动到指定元素
	 * */
	public void mouseMoveToElement(WebElement element) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(element);
		mouse.perform();
	}

	/**
	 * 添加cookies,做自动登陆的必要方法
	 * */
	public void addCookies(int sleepTime) {
		pause(sleepTime);
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie c : cookies) {
			System.out.println(c.getName() + "->" + c.getValue());
			if (c.getName().equals("Sessionid")) {
				Cookie cook = new Cookie(c.getName(), c.getValue());
				driver.manage().addCookie(cook);
				System.out.println(c.getName() + "->" + c.getValue());
				System.out.println("添加成功");
			} else {
				System.out.println("没有找到Sessionid");
			}

		}

	}

	/** 获得CSS value */
	public String getCSSValue(WebElement e, String key) {

		return e.getCssValue(key);
	}

	/** 使用testng的assetTrue方法 */
	public void assertTrue(WebElement e, String content) {
		String str = e.getText();
		Assert.assertTrue(str.contains(content), "字符串数组中不含有：" + content);

	}

	/** 跳出frame */
	public void outFrame() {

		driver.switchTo().defaultContent();
	}

	// webdriver中可以设置很多的超时时间
	/** implicitlyWait。识别对象时的超时时间。过了这个时间如果对象还没找到的话就会抛出NoSuchElement异常 */
	public void implicitlyWait(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	/** setScriptTimeout。异步脚本的超时时间。webdriver可以异步执行脚本，这个是设置异步执行脚本脚本返回结果的超时时间 */
	public void setScriptTimeout(int timeOut) {
		driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
	}

	/**
	 * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
	 * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
	 */

	public void hasLoadPageSucceeded(int pageLoadTime) {

		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);

	}

	/** 根据元素来获取此元素的定位值 */
	public String getLocatorByElement(WebElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to find the string [" + expectText + "]");

		}

		return expect;

	}

	/**
	 * 这是一堆相同的elements中 选择 其中方的 一个 然后在这个选定的中 继续定位
	 * */
	public WebElement getOneElement(By bys, By by, int index) {
		return findElementsBy(bys).get(index).findElement(by);
	}

	/**
	 * 上传文件，需要点击弹出上传照片的窗口才行
	 * 
	 * @param browser
	 *            使用的浏览器名称
	 * @param file
	 *            需要上传的文件及文件名
	 */
	public void handleUpload(String browser, File file) {
		String filePath = file.getAbsolutePath();
		String executeFile = "res/script/autoit/Upload.exe";
		String cmd = "\"" + executeFile + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 填写详细地址
	 * 
	 * @param elementProvince
	 *            省下拉列表
	 * @param province
	 *            写出省的名称
	 * @param elementCity
	 *            市下拉列表
	 * @param city
	 *            写出市的名称
	 * @param elementRegion
	 *            区下拉列表
	 * @param region
	 *            写出市的名称
	 * @param elementAddress
	 *            详细地址文本框元素
	 * @param address
	 *            填写详细地址镇与街道
	 */
	public void address(By elementProvince, String province, By elementCity, String city, By elementRegion, String region, By elementAddress, String address) {
		selectByText(elementProvince, province);
		selectByText(elementCity, city);
		selectByText(elementRegion, region);
		clear(findElementBy(elementAddress));
		type(findElementBy(elementAddress), address);
	}

	/**
	 * @Description 对于windows GUI弹出框，要求输入用户名和密码时，
	 *              seleniumm不能直接操作，需要借助http://modifyusername:modifypassword@yoururl 这种方法
	 * 
	 * */
	public void loginOnWinGUI(String username, String password, String url) {
		driver.get(username + ":" + password + "@" + url);
	}

	/** 检查元素是否显示 */
	public void isDisplayed(WebElement element) {
		if (element.isDisplayed() == true) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is displayed");
		} else if (element.isDisplayed() == false) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is not displayed");
			Assert.fail("The element: [" + getLocatorByElement(element, ">") + "] is not displayed");
		}
	}
	
	/** 检查元素是否被勾选 */
	public boolean isSelected(WebElement element) {
		boolean flag = false;
		if (element.isSelected() == true) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is selected");
			flag = true;
		} else if (element.isSelected() == false) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is not selected");
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断实际文本是否包含期望文本
	 * 
	 * @param actual
	 *            实际文本
	 * @param expect
	 *            期望文本
	 */
	public void isContains(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			logger.error("The [" + actual + "] is not contains [" + expect + "]");
			Assert.fail("The [" + actual + "] is not contains [" + expect + "]");
		}
		logger.info("The [" + actual + "] is contains [" + expect + "]");
	}

	/** 判断对应的图是不是存在 */
	public void isImgExist(Screen s, String imgPath) {

		if (s.exists(imgPath) != null) {
			logger.info("找到了指定的UI图：[" + imgPath + "]并且一致");
		} else {
			logger.error("没有找到此UI图：[" + imgPath + "]");
			Assert.fail("没有找到此UI图：[" + imgPath + "]");
		}

	}

	/** 获得屏幕的分辨率 - 宽 */
	public static double getScreenWidth() {
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	/** 判断元素是不是存在 */
	public boolean doesElementExist(By by) {
		try {
			driver.findElement(by);
			logger.info("元素：" + getLocatorByElement(findElementBy(by), ">") + "存在");
			return true;
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			logger.error("元素：" + getLocatorByElement(findElementBy(by), ">") + "不存在");
			return false;
		}
	}
}
