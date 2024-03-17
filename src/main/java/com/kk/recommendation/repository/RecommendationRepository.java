package com.kk.recommendation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kk.recommendation.entity.Recommendation;

import jakarta.persistence.Transient;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

	List<Recommendation> findByStatus(String status);

	// @Query("select expectedDate from Stocks")
	// List<Date> getExpectedDates();

	// @Transactional
	// @Modifying
	// @Query("UPDATE Stocks SET progress=?1 WHERE expectedDate=?2")
	// void updateProgress(String progress,Date expectedDate);

	List<Recommendation> findByIsCompleted(boolean b);

	List<Recommendation> findByStockId(Long id);

//	
//	@Transient
//	@Modifying
//	@Query("update recommendation set isComplete=?1,status=?2, returnPercentage=?3 where stockId=?4")
//	public void updateRecommendation(boolean isCompleted, String status, Double returnPercentage, Long stockId);

}
