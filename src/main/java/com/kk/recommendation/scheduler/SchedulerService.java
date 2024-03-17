package com.kk.recommendation.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kk.recommendation.service.RecommendationService;

@Component
public class SchedulerService {

	@Autowired
	RecommendationService recommendationService;

	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void schedulerOnEvery5Minutes() {
		System.out.println("Scheduler run ");
		recommendationService.validateRecommendationData();

	}

}
