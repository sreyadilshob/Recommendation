package com.kk.recommendation.model;

public class RecommendationModel {

	String stockSymbol;
	String callType;
	String startDate;
	Long startPrice;
	String targetDate;
	Long targetPrice;
	Long stopLossPrice;
	String channel;

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Long getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Long startPrice) {
		this.startPrice = startPrice;
	}

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public Long getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(Long targetPrice) {
		this.targetPrice = targetPrice;
	}

	public Long getStopLossPrice() {
		return stopLossPrice;
	}

	public void setStopLossPrice(Long stopLossPrice) {
		this.stopLossPrice = stopLossPrice;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
