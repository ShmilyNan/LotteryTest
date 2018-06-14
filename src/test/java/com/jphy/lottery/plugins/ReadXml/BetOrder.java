package com.jphy.lottery.plugins.ReadXml;

public class BetOrder {
	/**
	 * @author Lance
	 */
	
	private String betRange;
	private String playType;
	private String betContent;
	private int mutiple;
	private String amount;
	private String spend;
	private String resultNum;
	private String drawnAmount;
	private String balance;

	/**
	 * @return the betRange
	 */
	public String getBetRange() {
		return betRange;
	}

	/**
	 * @param betRange
	 *            the betRange to set
	 */
	public void setBetRange(String betRange) {
		this.betRange = betRange;
	}

	/**
	 * @return the PlayType
	 */
	public String getPlayType() {
		return playType;
	}

	/**
	 * @param playType
	 *            the playType to set
	 */
	public void setPlayType(String playType) {
		this.playType = playType;
	}

	/**
	 * @return the betContent
	 */
	public String getBetContent() {
		return betContent;
	}

	/**
	 * @param betContent
	 *            the betContent to set
	 */
	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	/**
	 * @return the mutiple
	 */
	public Integer getMutiple() {
		return mutiple;
	}

	/**
	 * @param mutiple
	 *            the mutiple to set
	 */
	public void setMutiple(Integer mutiple) {
		this.mutiple = mutiple;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the spend
	 */
	public String getSpend() {
		return spend;
	}
	
	/**
	 * @param spend
	 * 			the spend to set
	 */
	public void setSpend(String spend) {
		this.spend = spend;
	}

	/**
	 * @return
	 */
	public String getDrawnAmount() {
		return drawnAmount;
	}

	/**
	 * @param drawnAmount
	 */
	public void setDrawnAmount(String drawnAmount) {
		this.drawnAmount = drawnAmount;
	}

	/**
	 * @return balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 * 			th balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
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

	@Override
	public String toString() {
		return "BetOrder [betRange=" + betRange + ", playType=" + playType + ", betContent=" + betContent + ", mutiple=" + mutiple + ", amount=" + amount +", spend=" + spend +", drawnAmount=" + drawnAmount +", balance=" + balance + "]";
	}
}
