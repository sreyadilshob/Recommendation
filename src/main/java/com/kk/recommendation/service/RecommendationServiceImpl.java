package com.kk.recommendation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kk.recommendation.entity.Recommendation;
import com.kk.recommendation.model.RecommendationModel;
import com.kk.recommendation.model.Stock;
import com.kk.recommendation.model.StockPrice;
import com.kk.recommendation.repository.RecommendationRepository;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	@Autowired
	RecommendationRepository recomRepo;

	@Autowired
	RestTemplate restTemplate;

	@Value("${url.stock.api}")
	String stockApiUrl;
	
	@Value("${url.stock.stockid.api}")
	String stockIdApiUrl;

	@Override
	public List<Recommendation> getAllRecommendationDetails() {
		List<Recommendation> recommendations = recomRepo.findAll();
		for(Recommendation recommendation : recommendations) {
			Long stockId = recommendation.getStockId();
			ResponseEntity<Stock> response = restTemplate.getForEntity(stockIdApiUrl, Stock.class, stockId);
			if(!response.getStatusCode().is2xxSuccessful()) {
				System.out.println("############################################");
			}
			recommendation.setStock(response.getBody());
		}
		return recommendations;
	}

	@Override
	public List<Recommendation> getRecommendationBySymbol(String symbol) {

		Stock stock = getStockResponse(symbol);

		return recomRepo.findByStockId(stock.getId());
	}

	private Stock getStockResponse(String symbol) {
		ResponseEntity<Stock> response = restTemplate.getForEntity(stockApiUrl, Stock.class, symbol);
		if (response.getStatusCode().is2xxSuccessful()) {
			System.out.println("Response status is successful");
		}
		return response.getBody();
	}

	@Override
	public RecommendationModel saveRecommendation(RecommendationModel recModel) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Recommendation recommendation = new Recommendation();
		recommendation.setCallType(recModel.getCallType());
		recommendation.setStartDate(dateFormat.parse(recModel.getStartDate() + " 00:00:00"));
		recommendation.setStartPrice(recModel.getStartPrice());
		recommendation.setTargetDate(dateFormat.parse(recModel.getTargetDate() + " 00:00:00"));
		recommendation.setTargetPrice(recModel.getTargetPrice());
		recommendation.setStopLossPrice(recModel.getStopLossPrice());
		recommendation.setChannel(recModel.getChannel());
		recommendation.setCurrDate(new Date());
		recommendation.setCompleted(false);
		// recommendation.setStatus(null); //hardcoded
		// recommendation.setReturnPercentage(0L); //hardcopded
		
		Stock stock = getStockResponse(recModel.getStockSymbol());
		recommendation.setStockId(stock.getId());
		
		recomRepo.save(recommendation);
		return recModel;

	}

	@Override
	public List<Recommendation> getRecommendationByStockStatus(String status) {
		return recomRepo.findByStatus(status);
	}


	@Override
	public void validateRecommendationData() {
		List<Recommendation> recomList = recomRepo.findByIsCompleted(false);
		for(Recommendation recommendation : recomList) {
			Long stockId = recommendation.getStockId();
			ResponseEntity<Stock> response = restTemplate.getForEntity(stockIdApiUrl, Stock.class, stockId);
			if(response.getStatusCode().is2xxSuccessful()) {
				Stock stock = response.getBody();
				List<StockPrice> stockPrices = stock.getStockPrices();			
				for(StockPrice price : stockPrices) {
					updateRecommendationData(recommendation, price);				
				}
			}
		}
		
	}
	
	private void updateRecommendationData(Recommendation recommendation, StockPrice price) {
		if(recommendation.getTargetPrice() < price.getClose() || recommendation.getTargetPrice() < price.getHigh()) {
			recommendation.setCompleted(true);
			recommendation.setStatus("PROFIT");
			recommendation.setReturnPercentage(getReturnPercentage(recommendation.getStartPrice() ,price.getClose()));
		}else if(price.getClose() < recommendation.getStopLossPrice() || price.getLow() < recommendation.getStopLossPrice()) {
			recommendation.setCompleted(true);
			recommendation.setStatus("PROFIT");
			recommendation.setReturnPercentage(getReturnPercentage(recommendation.getStartPrice() ,price.getClose()));
		} else if(new Date().after(recommendation.getTargetDate())) {
			recommendation.setCompleted(true);
			recommendation.setReturnPercentage(getReturnPercentage(recommendation.getStartPrice() ,price.getClose()));
			if(price.getClose() < recommendation.getStartPrice()) {
				recommendation.setStatus("LOSS");
			} else {
				recommendation.setStatus("PROFIT");
			}
		}
		
	}

	public Double getReturnPercentage(Long startPrice, Double currentPrice) {
		return (currentPrice-startPrice)*100/startPrice;
	}

}
