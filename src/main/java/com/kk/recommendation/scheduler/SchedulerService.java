package com.kk.recommendation.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kk.recommendation.service.RecommendationService;

@Component
public class SchedulerService {

	@Autowired
	RecommendationService recommendationService;

	//cron = "0 0/1 * 1/1 * ?"   5 min
	//cron = "35 9 * * * ?"	Every Day 9:35 AM
	@Scheduled(cron = "40 9 * * * ?")
	public void schedulerForEveryDayAt9AM() {
		System.out.println("Scheduler run ");
		recommendationService.validateRecommendationDataDaily();

	}

}
