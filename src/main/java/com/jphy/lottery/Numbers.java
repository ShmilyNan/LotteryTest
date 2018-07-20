package com.jphy.lottery;

public class Numbers {
	/**
	 * @author Lance
	 */
	private String number;
	private String sumOfWinNum;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSumOfWinNum() {
		return sumOfWinNum;
	}

	public void setSumOfWinNum(String sumOfWinNum) {
		this.sumOfWinNum = sumOfWinNum;
	}

	@Override
	public String toString() {
		return "Numbers [number=" + number+", sumOfWinNum=" + sumOfWinNum+ "]";
	}
}
