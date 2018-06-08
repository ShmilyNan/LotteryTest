package com.jphy.lottery.plugins.arrow;

import com.jphy.lottery.plugins.arrow.utils.ConfigReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * TestNG retry Analyzer.
 * 
 * @author kevinkong
 * 
 */
public class TestngRetry implements IRetryAnalyzer {
	static {
		PropertyConfigurator.configure("./src/test/resources/config/log4j.properties");
	}
	private static Logger logger = Logger.getLogger(TestngRetry.class);
	private int retryCount = 1;
	private static int maxRetryCount;

	static {
		ConfigReader config = ConfigReader.getInstance();
		maxRetryCount = config.getRetryCount();
		logger.info("RetryCount=" + maxRetryCount);
		logger.info("SourceDir=" + config.getSourceCodeDir());
		logger.info("SourceEncoding=" + config.getSrouceCodeEncoding());
	}

	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			String message = "Retry for： [" + result.getName() + "] on class [" + result.getTestClass().getName() + "] retry " + retryCount + " times";
			logger.info(message);
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}

	public static int getMaxRetryCount() {
		return maxRetryCount;
	}

	public int getRetryCount() {
		return retryCount;
	}

}
