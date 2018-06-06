package com.lottery.apitest;

public class BetOrder {
	/**
	 * @author Lance
	 */
	
	private int orderId;
	private String betRange;
	private String playType;
	private String betContent;
	private int mutiple;
	private int amount;
	private int spend;
	private double drawnAmount;
	private double balance;

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 * 			th orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

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
	public int getMutiple() {
		return mutiple;
	}

	/**
	 * @param mutiple
	 *            the mutiple to set
	 */
	public void setMutiple(int mutiple) {
		this.mutiple = mutiple;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * @return the spend
	 */
	public int getSpend() {
		return spend;
	}
	
	/**
	 * @param spend
	 * 			the spend to set
	 */
	public void setSpend(int spend) {
		this.spend = spend;
	}

	/**
	 * @return the drawnAmount
	 */
	public double getDrawnAmount() {
		return drawnAmount;
	}

	/**
	 * @param drawnAmount
	 * 			the drawnAmount to set
	 */
	public void setDrawnAmount(double drawnAmount) {
		this.drawnAmount = drawnAmount;
	}

	/**
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 * 			th balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "BetOrder [betRange=" + betRange + ", playType=" + playType + ", betContent=" + betContent + ", mutiple=" + mutiple + ", amount=" + amount +", spend=" + spend +", drawnAmount=" + drawnAmount +", balance=" + balance + "]";
	}
}
