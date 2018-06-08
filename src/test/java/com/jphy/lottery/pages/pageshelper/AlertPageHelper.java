package com.jphy.lottery.pages.pageshelper;

import com.jphy.lottery.util.SeleniumUtil;

public class AlertPageHelper {

	/** 登录出错的提示信息 */
	public static void checkAlertInfo(SeleniumUtil seleniumUtil, int waitMillisecondsForAlert) {
		seleniumUtil.isTextCorrect(seleniumUtil.switchToPromptedAlertAfterWait(waitMillisecondsForAlert).getText(), "用户名或密码错误,请重新登录!");
	}

}
