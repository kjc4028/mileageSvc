package com.demo.mileage_service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.mileage_service.mileage.MileageEntity;
import com.demo.mileage_service.mileage.MileageService;
import com.demo.mileage_service.mileage.hst.MileageHstEntity;
import com.demo.mileage_service.mileage.hst.MileageHstService;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.place.PlaceService;
import com.demo.mileage_service.review.ReviewEntity;
import com.demo.mileage_service.review.ReviewService;
import com.demo.mileage_service.review.AttFile.ReviewPhotoEntity;
import com.demo.mileage_service.review.AttFile.ReviewPhotoRepository;

@SpringBootTest
class MileageServiceApplicationTests {

	@Autowired
	ReviewService reviewService;
	
	@Autowired
	PlaceService placeService;

	@Autowired
	MileageService mileageService;

	@Autowired
	MileageHstService mileageHstService;

	@Autowired
	ReviewPhotoRepository reviewPhotoRepository;
	
	final String testPlaceId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";
	final String testReviewId = "111";

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
		ReviewEntity reviewEntity1 = ReviewEntity.builder(uuid).build();


		List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder("1111111").build();
		//reviewPhotoRepository.save(reviewPhotoEntity);
		//em.persist(reviewPhotoEntity);
		reviewPhotoList.add(reviewPhotoEntity);
	
		

		ReviewEntity reviewEntity = ReviewEntity.builder(uuid)
		.reviewCts("222222")
		.userId("kjc1")
		.placeEntity(placeEntity)
		.reviewPhotoList(new ArrayList<>())
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
		
		//reviewService.save(reviewEntity);
		//reviewPhotoRepository.save(reviewPhotoEntity);
		
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
		.reviewCts("test1234")
		//.reviewCts(null)
		.userId("kjc1")
		.placeEntity(placeEntity)
		.reviewPhotoList(null)
		.build();

		reviewService.deleteReview(reviewEntity);
	}

	@Test
	void ReviewBnsFInd(){
		MileageHstEntity mileageHstEntity = mileageHstService.findByReviewIdAndMileageCls("rv03","보너스점수 적립");
		System.out.println("bnstest : " + mileageHstEntity);
	}

}
