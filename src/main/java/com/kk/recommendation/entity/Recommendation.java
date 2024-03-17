package com.kk.recommendation.entity;

import java.util.Date;

import com.kk.recommendation.model.Stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long stockId;
	String callType;
	Date startDate;
	Long startPrice;
	Date targetDate;
	Long targetPrice;
	Long stopLossPrice;
	String channel;
	Date currDate;
	String status;
	Double returnPercentage;
	boolean isCompleted;
	
	@Transient
	Stock stock;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date date) {
		this.startDate = date;
	}


	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}



	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getReturnPercentage() {
		return returnPercentage;
	}

	public void setReturnPercentage(Double returnPercentage) {
		this.returnPercentage = returnPercentage;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Long getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Long startPrice) {
		this.startPrice = startPrice;
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}


}
