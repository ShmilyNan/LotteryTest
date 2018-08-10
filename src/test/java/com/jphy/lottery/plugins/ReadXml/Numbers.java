package com.jphy.lottery.plugins.ReadXml;

public class Numbers {
	/**
	 * @author Lance
	 */
	private String number;
	private String resultNum;

	/**
	 * @return the betRange
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param betRange
	 *            the betRange to set
	 */
	public void setNumber(String betRange) {
		this.number = betRange;
	}

	/**
	 *
	 * @return resultNum
	 */
	public String getResultNum() {
		return resultNum;
	}

	/**
	 *
	 * @param resultNum
	 * 			the resultNum to set
	 */
	public void setResultNum(String resultNum) {
		this.resultNum = resultNum;
	}

	//@Override
	//public String toString() {
	//	return "BetOrder [betRange=" + betRange + ", playType=" + playType + ", betContent=" + betContent + ", mutiple=" + mutiple + ", amount=" + amount +", spend=" + spend +", drawnAmount=" + drawnAmount +", balance=" + balance + "]";
	//}
}
