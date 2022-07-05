package com.demo.mileage_service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.demo.mileage_service.mileage.MileageEntity;
import com.demo.mileage_service.mileage.MileageService;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.place.PlaceService;
import com.demo.mileage_service.review.ReviewEntity;
import com.demo.mileage_service.review.ReviewService;

@SpringBootTest
class MileageServiceApplicationTests {

	@Autowired
	ReviewService reviewService;
	
	@Autowired
	PlaceService placeService;

	@Autowired
	MileageService mileageService;

	final String testPlaceId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
	final String testReviewId = "ea71431a-0438-43a6-9d97-d5ed5818f7cc";

	@Test
	void PlaceInsert(){
		String uuid = UUID.randomUUID().toString();
		PlaceEntity placeEntity = PlaceEntity.builder(uuid).placeNm("장소2").build();
		placeService.insertPlace(placeEntity);
	}

	@Test
	void DummyuserJoin(){
		String uuid = UUID.randomUUID().toString();
		MileageEntity mileageEntity = MileageEntity.builder(uuid).point(0).build();
		mileageService.insertMileageInfo(mileageEntity);
		
	}

	@Test
	void ReviewInertTest() {
		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		String uuid = UUID.randomUUID().toString();
		ReviewEntity reviewEntity = ReviewEntity.builder(uuid)
		.reviewCts("test1")
		.userId("kjc1")
		.placeEntity(placeEntity)
		.reviewPhotoList(null)
		.build();
		//reviewService.save(revieweEntity);

		reviewService.reviewInsert(reviewEntity);
	}

	@Test
	void ReviewModTest(){
		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		ReviewEntity reviewEntity = ReviewEntity.builder(testReviewId)
		//.reviewCts("test1234")
		//.reviewCts(null)
		.userId("kjc1")
		.placeEntity(placeEntity)
		.reviewPhotoList(null)
		.build();

		reviewService.modReview(reviewEntity);
		
	}

	@Test
	void ReviewDeleteTest(){
		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		ReviewEntity reviewEntity = ReviewEntity.builder(testReviewId)
		//.reviewCts("test1234")
		//.reviewCts(null)
		.userId("kjc1")
		//.placeEntity(placeEntity)
		.reviewPhotoList(null)
		.build();

		reviewService.deleteReview(reviewEntity);
	}


}
