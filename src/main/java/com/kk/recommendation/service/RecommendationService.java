package com.kk.recommendation.service;

import java.text.ParseException;
import java.util.List;

import com.kk.recommendation.entity.Recommendation;
import com.kk.recommendation.model.RecommendationModel;

public interface RecommendationService {

	List<Recommendation> getAllRecommendationDetails();

	List<Recommendation> getRecommendationByStockStatus(String status);

	RecommendationModel saveRecommendation(RecommendationModel stockdata) throws ParseException;

	List<Recommendation> getRecommendationBySymbol(String symbol);

	void validateRecommendationData();

}
