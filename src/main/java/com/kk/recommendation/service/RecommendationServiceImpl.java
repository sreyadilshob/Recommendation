package com.kk.recommendation.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

		Recommendation recommendation = new Recommendation();
		recommendation.setCallType(recModel.getCallType());
		recommendation.setStartDate(LocalDate.parse(recModel.getStartDate()));
		recommendation.setStartPrice(recModel.getStartPrice());
		recommendation.setTargetDate(LocalDate.parse(recModel.getTargetDate()));
		recommendation.setTargetPrice(recModel.getTargetPrice());
		recommendation.setStopLossPrice(recModel.getStopLossPrice());
		recommendation.setChannel(recModel.getChannel());
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
	public Recommendation validateRecommendationFromHistoryById(Long recomID) {
		Optional<Recommendation> recomObj = recomRepo.findById(recomID);
		if(recomObj.isPresent()) {
			Recommendation recommendation = recomObj.get();
			
			System.out.println("Fetching Stock Details for stockID: "+recommendation.getStockId());
			ResponseEntity<Stock> response = restTemplate.getForEntity(stockIdApiUrl, Stock.class, recommendation.getStockId());
			if(response.getStatusCode().is2xxSuccessful()) {
				Stock stock = response.getBody();
				List<StockPrice> stockPrices = stock.getStockPrices();		
				List<StockPrice> sortedPriceList = stockPrices.stream()
					.filter(x -> recommendation.getStartDate().isBefore(x.getDate()) && 
							(recommendation.getTargetDate().isAfter(x.getDate()) ||  recommendation.getTargetDate().isEqual(x.getDate())) )
					.sorted(Comparator.comparing(StockPrice::getDate))
					.collect(Collectors.toList());
				
				for(StockPrice price : sortedPriceList) {
					System.out.println("Date:"+price.getDate()+" #Open:"+price.getOpen()+" #Close:"
							+price.getClose()+" #High:"+price.getHigh()+" #Low:"+price.getLow());
					
					Double returnPerc = getReturnPercentage(recommendation.getStartPrice() ,price.getClose());
					if(price.getHigh() >= recommendation.getTargetPrice()) {
						updateRecomObject(recommendation, true, "PROFIT", returnPerc, "TARGET", price.getDate());
						break;
					}else if(price.getLow() <= recommendation.getStopLossPrice()) {
						updateRecomObject(recommendation, true, "LOSS", returnPerc, "STOPLOSS", price.getDate());
						break;
					} else if(price.getDate().isEqual(recommendation.getTargetDate())) {
						updateRecomObject(recommendation, true, price.getClose() < recommendation.getStartPrice() ? "LOSS" : "PROFIT"
							, returnPerc, "DATE", price.getDate());
						break;
					} else if(price.getDate().isBefore(recommendation.getTargetDate())) {
						updateRecomObject(recommendation, false, price.getClose() < recommendation.getStartPrice() ? "LOSS" : "PROFIT"
							, returnPerc,"ACTIVE", price.getDate());
					}
					System.out.println("#Completed: "+recommendation.isCompleted()+" #Status: "
							+recommendation.getStatus()+" #return: "+recommendation.getReturnPercentage());
					recomRepo.save(recommendation);
				}
			}
		}
		return recomRepo.findById(recomID).get();
	}

	@Override
	public void validateRecommendationDataDaily() {
		List<Recommendation> recomList = recomRepo.findByIsCompleted(false);
		for(Recommendation recommendation : recomList) {
			Long stockId = recommendation.getStockId();
			System.out.println("Validating Recommendation for stockID: "+stockId);
			ResponseEntity<Stock> response = restTemplate.getForEntity(stockIdApiUrl, Stock.class, stockId);
			if(response.getStatusCode().is2xxSuccessful()) {
				Stock stock = response.getBody();
				//List<StockPrice> stockPrices = stock.getStockPrices();		
				Optional<StockPrice> latestPriceObj = stock.getStockPrices().stream().max(Comparator.comparing(StockPrice::getDate));
				if(latestPriceObj.isPresent()) {
					StockPrice latestPrice = latestPriceObj.get();
					System.out.println("Latest Price fetched -> Date:"+latestPrice.getDate()+" #Open:"+latestPrice.getOpen()+" #Close:"
							+latestPrice.getClose()+" #High:"+latestPrice.getHigh()+" #Low:"+latestPrice.getLow());
					updateRecommendationData(recommendation, latestPrice);
				}
				recomRepo.save(recommendation);
			}
		}
		
	}
	
	private void updateRecommendationData(Recommendation recommendation, StockPrice price) {
		
		Double returnPerc = getReturnPercentage(recommendation.getStartPrice() ,price.getClose());
		if(price.getHigh() >= recommendation.getTargetPrice()) {
			updateRecomObject(recommendation, true, "PROFIT", returnPerc, "TARGET", price.getDate());
		}else if(price.getLow() <= recommendation.getStopLossPrice()) {
			updateRecomObject(recommendation, true, "LOSS", returnPerc, "STOPLOSS", price.getDate());
		} else if(price.getDate().isEqual(recommendation.getTargetDate())) {
			updateRecomObject(recommendation, true, price.getClose() < recommendation.getStartPrice() ? "LOSS" : "PROFIT", returnPerc
					, "DATE", price.getDate());
		} else if(price.getDate().isBefore(recommendation.getTargetDate())) {
			updateRecomObject(recommendation, false, price.getClose() < recommendation.getStartPrice() ? "LOSS" : "PROFIT", returnPerc
					, "ACTIVE", price.getDate());
		}
		
	}

	private void updateRecomObject(Recommendation recommendation, boolean completed, String status, Double returnPerc, String reason, LocalDate cDate) {
		recommendation.setCompleted(completed);
		recommendation.setStatus(status);
		recommendation.setReturnPercentage(returnPerc);
		recommendation.setCompletionDate(cDate);
		recommendation.setCompletionReason(reason);
		recommendation.setUpdatedTime(new Date());
	}

	public Double getReturnPercentage(Long startPrice, Double currentPrice) {
		return (currentPrice-startPrice)*100/startPrice;
	}

}
