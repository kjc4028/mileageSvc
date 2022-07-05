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
		PlaceEntity placeEntity = PlaceEntity.builder("54644171-f135-4580-90bf-2b1f7ffea309").build();
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
		PlaceEntity placeEntity = PlaceEntity.builder("54644171-f135-4580-90bf-2b1f7ffea309").build();
		String uuid = "adc881a6-1227-4ae3-ab06-43699c6c6924";
		ReviewEntity reviewEntity = ReviewEntity.builder(uuid)
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
		PlaceEntity placeEntity = PlaceEntity.builder("54644171-f135-4580-90bf-2b1f7ffea309").build();
		String uuid = "ba04b7c1-0dff-451c-a88e-6a7157358fe5";
		ReviewEntity reviewEntity = ReviewEntity.builder(uuid)
		//.reviewCts("test1234")
		//.reviewCts(null)
		.userId("kjc1")
		.placeEntity(placeEntity)
		.reviewPhotoList(null)
		.build();

		reviewService.deleteReview(reviewEntity);
	}


}
