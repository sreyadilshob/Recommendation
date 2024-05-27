package com.kk.recommendation.entity;

import java.time.LocalDate;
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
	LocalDate startDate;
	Long startPrice;
	LocalDate targetDate;
	Long targetPrice;
	Long stopLossPrice;
	String channel;
	Date updatedTime;
	String status;
	Double returnPercentage;
	boolean isCompleted;
	String completionReason;
	LocalDate completionDate;
	
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}


	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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

	public String getCompletionReason() {
		return completionReason;
	}

	public void setCompletionReason(String completionReason) {
		this.completionReason = completionReason;
	}

	public LocalDate getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}
}
