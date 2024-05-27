package com.kk.recommendation.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kk.recommendation.entity.Recommendation;
import com.kk.recommendation.model.RecommendationModel;
import com.kk.recommendation.model.RecommendationsModel;
import com.kk.recommendation.service.RecommendationService;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

	@Autowired
	RecommendationService rcomSvc;

	@GetMapping("/all")
	RecommendationsModel getAllRecommendationDetials() {
		RecommendationsModel recomsModel = new RecommendationsModel();
		recomsModel.setRecommendationList(rcomSvc.getAllRecommendationDetails());
		return recomsModel;
	}

	@GetMapping("/symbol/{symbol}")
	List<Recommendation> getRecommendationbySymbol(@PathVariable String symbol) {
		return rcomSvc.getRecommendationBySymbol(symbol);
	}

	@GetMapping("/stock/status/{status}")
	List<Recommendation> getRecommendationbyStatus(@PathVariable String status) {
		return rcomSvc.getRecommendationByStockStatus(status);
	}

	@PostMapping
	RecommendationModel saveRecommendation(@RequestBody RecommendationModel model) throws ParseException {
		return rcomSvc.saveRecommendation(model);
	}
	
//	@PostMapping("/all")
//	RecommendationModel saveAllRecommendation(@RequestBody RecommendationsModel model) throws ParseException {
//		return rcomSvc.saveAllRecommendations(model);
//	}
	
	@GetMapping("/validate/id/{recomId}")
	Recommendation getRecommendationbyStatus(@PathVariable Long recomId) {
		System.out.println("Starting to validate Recommendation: "+recomId);
		return rcomSvc.validateRecommendationFromHistoryById(recomId);
	}

}
