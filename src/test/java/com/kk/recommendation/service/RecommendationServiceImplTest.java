package com.kk.recommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kk.recommendation.entity.Recommendation;
import com.kk.recommendation.model.RecommendationModel;
import com.kk.recommendation.repository.RecommendationRepository;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest {
	
	@InjectMocks
	RecommendationServiceImpl rescomImpl;
	@Mock
	RecommendationRepository recomRepo;
	@Test
	public void saveRecommendation_testSaveRecommendation() throws ParseException {
		RecommendationModel recoModel = new RecommendationModel();
		recoModel.setStartDate("03-03-2024");
		recoModel.setTargetDate("08-08-2024");
		String stockSymbol = "abc";
		recoModel.setStockSymbol(stockSymbol);
		
		RecommendationModel response = rescomImpl.saveRecommendation(recoModel);		
		
		assertEquals(recoModel, response);
	}
	@Test
	public void saveRecommendation_testSaveRecommendation2() throws ParseException {
		RecommendationModel recoModel = new RecommendationModel();
		recoModel.setStartDate("03-03-2024");
		recoModel.setTargetDate("08-08-2024");
		String stockSymbol = "abc";
		recoModel.setStockSymbol(stockSymbol);
		
		RecommendationModel response = rescomImpl.saveRecommendation(recoModel);		
		
		assertEquals(recoModel, response);
	}
	
	@Test
	public void getAllRecommendationDetails_testingAllRecommendation() {
		List<Recommendation> listRecom = new ArrayList<>();
		Recommendation recom = new Recommendation();
		recom.setCallType("123");
		recom.setChannel("abc");
		recom.setStatus("completed");
		listRecom.add(recom);
		
		when(recomRepo.findAll()).thenReturn(listRecom);
		
		List<Recommendation> response = rescomImpl.getAllRecommendationDetails();
		
		assertEquals(listRecom, response);
	}
	
	@Test
	public void getReturnPercentage_test() {
		Double response = rescomImpl.getReturnPercentage(10L, 4D);
		assertEquals(-60, response);
	}
	
	/*@Test
	public void verifyDateAndUpdateStatus_testingIfandFor() throws ParseException {
		List<Recommendation> stockList = new ArrayList<>();
		Recommendation recom = new Recommendation();
		recom.setCallType("123");
		recom.setChannel("abc");
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	   // String date = "23-03-2024";
	   // Date formattedTargetDate = dateFormat.parse(date+" 00:00:00");
	    recom.setTargetDate(new Date());
		stockList.add(recom);
		
		when(recomRepo.findByIsCompleted(false)).thenReturn(stockList);
		
		when(recomRepo.saveAll(stockList)).thenReturn(stockList);		
		
		rescomImpl.verifyDateAndUpdateStatus();
	}	
	
	@Test
	public void verifyDateAndUpdateStatus_testingIfandFortest2() throws ParseException {
		List<Recommendation> stockList = new ArrayList<>();
		Recommendation recom = new Recommendation();
		recom.setCallType("123");
		recom.setChannel("abc");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    String date = "23-02-2024";
	    Date formattedTargetDate = dateFormat.parse(date+" 00:00:00");
	    recom.setTargetDate(formattedTargetDate);
		stockList.add(recom);
		
		when(recomRepo.findByIsCompleted(false)).thenReturn(stockList);
		
		when(recomRepo.saveAll(stockList)).thenReturn(stockList);		
		
		rescomImpl.verifyDateAndUpdateStatus();
	}	*/
	
}
