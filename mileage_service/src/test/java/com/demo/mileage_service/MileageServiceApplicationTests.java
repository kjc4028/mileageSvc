package com.demo.mileage_service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

	@Test
	void PlaceInsert(){
		String uuid = UUID.randomUUID().toString();
		PlaceEntity placeEntity = PlaceEntity.builder(uuid).placeNm("장소2").build();
		placeService.insertPlace(placeEntity);
	}


	@Test
	void ReviewInertTest() {
		PlaceEntity placeEntity = PlaceEntity.builder("8516dc50-bde3-4db3-b279-17302a7c13a7").build();
		String uuid = UUID.randomUUID().toString();
		ReviewEntity revieweEntity = ReviewEntity.builder(uuid)
		.reviewCts("test1")
		.userId("kjc1")
		.placeEntity(placeEntity)
		.reviewPhotoList(null)
		.build();
		//reviewService.save(revieweEntity);

		reviewService.reviewInsert(revieweEntity);
	}

	@Test
	void ReviewModTest(){
		PlaceEntity placeEntity = PlaceEntity.builder("8516dc50-bde3-4db3-b279-17302a7c13a7").build();
		String uuid = "08f2a4dc-8bf6-458c-a63b-2e7ddc7899f4";
		ReviewEntity revieweEntity = ReviewEntity.builder(uuid)
		.reviewCts("test1234")
		.userId("kjc1")
		.placeEntity(placeEntity)
		//.reviewPhotoList(null)
		.build();
		
		System.out.println(reviewService.modReview(revieweEntity));
	}

}
