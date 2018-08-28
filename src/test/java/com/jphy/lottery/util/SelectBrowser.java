package com.jphy.lottery.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;

/**
 * @author Lance
 * @decription 在不同的平台上选择对应的浏览器, 系统平台程序自动判断是什么平台
 */
public class SelectBrowser {
    static Logger logger = Logger.getLogger(SelectBrowser.class.getName());

    public WebDriver selectExplorerByName(String browser, ITestContext context) {
        Properties props = System.getProperties(); // 获得系统属性集
        String currentPlatform = props.getProperty("os.name"); // 操作系统名称
        logger.info("当前操作系统是 [" + currentPlatform + "]");
        logger.info("启动测试浏览器[" + browser + "]");
        String driverConfgFilePath = context.getCurrentXmlTest().getParameter("driverConfgFilePath");
        /** 声明好驱动的路径 */
        String chromedriver_win = PropertiesDataProvider.getTestData(driverConfgFilePath, "chromedriver_win");
        String chromedriver_linux = PropertiesDataProvider.getTestData(driverConfgFilePath, "chromedriver_linux");
        String chromedriver_mac = PropertiesDataProvider.getTestData(driverConfgFilePath, "chromedriver_mac");
        String iedriver = PropertiesDataProvider.getTestData(driverConfgFilePath, "iedriver");
        String geckodriver = PropertiesDataProvider.getTestData(driverConfgFilePath, "geckodriver");
        if (currentPlatform.toLowerCase().contains("win")) {

            if (browser.equalsIgnoreCase("ie")) {
                System.setProperty("webdriver.ie.driver", iedriver);
                DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
                ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                return new InternetExplorerDriver(ieCapabilities);
            } else if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", chromedriver_win);
                return new ChromeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.firefox.bin", "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
                return new FirefoxDriver();

            } else {

                logger.error("The [" + browser + "]" + " explorer is not applicable  for  [" + currentPlatform + "] OS");
                Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");

            }

        } else if (currentPlatform.toLowerCase().contains("linux")) {

            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", chromedriver_linux);
                return new ChromeDriver();

            } else if (browser.equalsIgnoreCase("firefox")) {
                return new FirefoxDriver();
            } else {
                logger.error("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
                Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
            }

        } else if (currentPlatform.toLowerCase().contains("mac")) {
            if (browser.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", chromedriver_mac);
                return new ChromeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                return new FirefoxDriver();
            } else {
                logger.error("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
                Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
            }

        } else
            logger.error("The [" + currentPlatform + "] is not supported for this automation frame,please change the OS(Windows,MAC or LINUX)");
        Assert.fail("The [" + currentPlatform + "] is not supported for this automation frame,please change the OS(Windows,MAC or LINUX)");

        return null;

    }

}
