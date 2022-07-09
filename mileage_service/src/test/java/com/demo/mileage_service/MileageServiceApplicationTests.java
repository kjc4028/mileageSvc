package com.demo.mileage_service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import com.demo.mileage_service.mileage.MileageEntity;
import com.demo.mileage_service.mileage.MileageService;
import com.demo.mileage_service.mileage.hst.MileageHstService;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.place.PlaceService;
import com.demo.mileage_service.review.ReviewEntity;
import com.demo.mileage_service.review.ReviewService;
import com.demo.mileage_service.review.photo.ReviewPhotoEntity;
import com.demo.mileage_service.review.photo.ReviewPhotoRepository;
import com.demo.mileage_service.review.photo.ReviewPhotoService;

/**
 * 해당 테스트는 시작할 때마다 기존 데이터를 초기화하고 진행하는 점 참고바랍니다.
 */
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
	ReviewPhotoService reviewPhotoService;

	@Autowired
	ReviewPhotoRepository reviewPhotoRepository;
	
	static String testPlaceId = "";
	static String testReviewId = "";
	static String userId_01 = "";
	static String userId_02 = "";

	@Test
	@DisplayName("장소등록")
	void PlaceInsert(){
		String uuid = UUID.randomUUID().toString();
		PlaceEntity placeEntity = PlaceEntity.builder(uuid).placeNm("테스트 장소").build();
		placeService.insertPlace(placeEntity);	
		testPlaceId = placeEntity.getPlaceId();

		assertNotNull(placeService.findById(testPlaceId).get());
	}

	@Test
	@DisplayName("마일리지가입")
	void MileageJoin(){
		String uuid = UUID.randomUUID().toString();
		MileageEntity mileageEntity = MileageEntity.builder(uuid).point(0).build();
		mileageService.insertMileageInfo(mileageEntity);
		userId_01 = mileageEntity.getUserId();

		assertNotNull(mileageService.findByUserId(userId_01));
	}

	@Test
	@DisplayName("마일리지가입 두번째 사용자")
	void MileageJoin2(){
		String uuid = UUID.randomUUID().toString();
		MileageEntity mileageEntity = MileageEntity.builder(uuid).point(0).build();
		mileageService.insertMileageInfo(mileageEntity);
		userId_02 = mileageEntity.getUserId();

		assertNotNull(mileageService.findByUserId(userId_02));
	}
	
	@Test
	@DisplayName("리뷰등록")
	@Description("내용 점수 : (1자 이상 텍스트 작성: 1점, 1장 이상 사진 첨부: 1점), 보너스 점수 : (첫 리뷰 작성: 1점)")
	void ReviewInertTest() {
		//전처리
		PlaceInsert(); //장소등록
		MileageJoin(); //마일리지가입

		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		String photo_uuid = UUID.randomUUID().toString();
		String review_uuid = UUID.randomUUID().toString();

		testReviewId = review_uuid;

		List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm("reviewImg.jpg")
		.build();
		
		reviewPhotoList.add(reviewPhotoEntity);

		ReviewEntity reviewEntity = ReviewEntity.builder(review_uuid)
		.reviewCts("리뷰등록 테스트 중 입니다.")
		.userId(userId_01)
		.reviewPhotoList(new ArrayList<>())
		.placeEntity(placeEntity)
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
		
		reviewService.reviewInsert(reviewEntity);

		assertNotNull(reviewService.findOneByReviewId(testReviewId));
		assertEquals(3, mileageService.findByUserId(userId_01).getPoint());
	}

	@Test
	@DisplayName("리뷰등록-장소첫리뷰점수, 텍스트점수")
	@Description("내용 점수 : (1자 이상 텍스트 작성: 1점, 1장 이상 사진 첨부: 1점), 보너스 점수 : (첫 리뷰 작성: 1점)")
	void ReviewInertTestOnlyText() {
		//전처리
		PlaceInsert(); //장소등록
		MileageJoin(); //마일리지가입
		
		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		String review_uuid = UUID.randomUUID().toString();

		testReviewId = review_uuid;

		ReviewEntity reviewEntity = ReviewEntity.builder(review_uuid)
		.reviewCts("리뷰등록 텍스트만 등록 테스트 중 입니다.")
		.userId(userId_01)
		.placeEntity(placeEntity)
		.reviewPhotoList(new ArrayList<>())
		.build();

		reviewService.reviewInsert(reviewEntity);

		assertNotNull(reviewService.findOneByReviewId(testReviewId));
		assertEquals(2, mileageService.findByUserId(userId_01).getPoint());		
	}

	@Test
	@DisplayName("리뷰등록-장소첫리뷰점수, 사진점수")
	@Description("내용 점수 : (1자 이상 텍스트 작성: 1점, 1장 이상 사진 첨부: 1점), 보너스 점수 : (첫 리뷰 작성: 1점)")
	void ReviewInertTestOnlyPhoto() {
		//전처리
		PlaceInsert(); //장소등록
		MileageJoin(); //마일리지가입
		
		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		String photo_uuid = UUID.randomUUID().toString();
		String review_uuid = UUID.randomUUID().toString();

		testReviewId = review_uuid;

		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm("reviewImg.jpg")
		.build();
		

		ReviewEntity reviewEntity = ReviewEntity.builder(review_uuid)
		.userId(userId_01)
		.placeEntity(placeEntity)
		.reviewPhotoList(new ArrayList<>())
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
		
		reviewService.reviewInsert(reviewEntity);

		assertNotNull(reviewService.findOneByReviewId(testReviewId));
		assertEquals(2, mileageService.findByUserId(userId_01).getPoint());		
	}

	@Test
	@DisplayName("리뷰등록 첫리뷰가 아닌 경우")
	@Description("사용자 입장에서 본 '첫 리뷰'일 때 보너스 점수를 부여 띠라서, 두번째 리뷰인 경우")
	void ReviewInertUser2Test() {
		//전처리
		ReviewInertTest();//첫번째 사용자 리뷰 등록
		MileageJoin2(); //두번째 사용자 가입

		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		String photo_uuid = UUID.randomUUID().toString();
		String review_uuid = UUID.randomUUID().toString();

		testReviewId = review_uuid;

		List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm("user02_reviewImg.jpg")
		.build();
		
		reviewPhotoList.add(reviewPhotoEntity);

		ReviewEntity reviewEntity = ReviewEntity.builder(review_uuid)
		.reviewCts("두번째 사용자 리뷰등록 테스트 중 입니다.")
		.userId(userId_02)
		.reviewPhotoList(new ArrayList<>())
		.placeEntity(placeEntity)
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
		
		reviewService.reviewInsert(reviewEntity);

		assertNotNull(reviewService.findOneByReviewId(testReviewId));
		assertEquals(2, mileageService.findByUserId(userId_02).getPoint());

	}

	@Test
	@DisplayName("리뷰등록 첫번째 리뷰가 삭제된 경우 등록")
	@Description("첫번째 사용자 첫번째 리뷰 삭제 후 두번째 사용자 첫번째 리뷰 등록시 보너스 점수 부여")
	void ReviewDelUser1InertUser2Test() {
		//전처리
		ReviewInertTest();//첫번째 사용자 리뷰 등록
		MileageJoin2(); //두번째 사용자 가입
		ReviewDeleteTest(); //첫번째 게시물 삭제

		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build();
		String photo_uuid = UUID.randomUUID().toString();
		String review_uuid = UUID.randomUUID().toString();

		testReviewId = review_uuid;

		List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm("user02_reviewImg.jpg")
		.build();
		
		reviewPhotoList.add(reviewPhotoEntity);

		ReviewEntity reviewEntity = ReviewEntity.builder(review_uuid)
		.reviewCts("두번째 사용자 리뷰등록 테스트 중 입니다.")
		.userId(userId_02)
		.reviewPhotoList(new ArrayList<>())
		.placeEntity(placeEntity)
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
		
		reviewService.reviewInsert(reviewEntity);

		assertNotNull(reviewService.findOneByReviewId(testReviewId));
		assertEquals(3, mileageService.findByUserId(userId_02).getPoint());

	}

	@Test
	@DisplayName("리뷰중복등록")
	@Description("한 사용자는 장소마다 리뷰를 1개만 작성가능")
	void ReviewDupleInertTest() {
		//전처리
		ReviewInertTest(); // 첫번째 리뷰 등록

		PlaceEntity placeEntity = PlaceEntity.builder(testPlaceId).build(); //동일한장소에 리뷰 등록진행
		String photo_uuid = UUID.randomUUID().toString();
		String review_uuid = UUID.randomUUID().toString();

		testReviewId = review_uuid;

		List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm("dup_reviewImg.jpg")
		.build();
		
		reviewPhotoList.add(reviewPhotoEntity);

		ReviewEntity reviewEntity = ReviewEntity.builder(review_uuid)
		.reviewCts("리뷰 중복 등록 테스트 중 입니다.")
		.userId(userId_01)
		.placeEntity(placeEntity)
		.reviewPhotoList(new ArrayList<>())
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
		
		reviewService.reviewInsert(reviewEntity);

		assertNull(reviewService.findOneByReviewId(testReviewId));
	}

	@Test
	@DisplayName("리뷰수정")
	void ReviewModTest(){
		//전처리
		ReviewInertTest(); //리뷰등록

		String modReviewCts = "리뷰내용 수정 테스트 입니다.";
		String modPhotoFileNm = "add_reviewImg.jpg";

		ReviewEntity reviewEntity = reviewService.findOneByReviewId(testReviewId);
		//리뷰내용 수정
		reviewEntity.setReviewCts(modReviewCts);

		String photo_uuid = UUID.randomUUID().toString();

		//리뷰사진 추가
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm(modPhotoFileNm)
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);

		reviewService.modReview(reviewEntity);

		ReviewEntity reviewEntityTestRs =  reviewService.findOneByReviewId(testReviewId);
		ReviewPhotoEntity reviewPhotoEntityTestRs =  reviewPhotoService.findOneByPhotoId(photo_uuid);
		
		assertNotNull(reviewEntityTestRs);
		assertEquals(modReviewCts, reviewEntityTestRs.getReviewCts());
		assertEquals(modPhotoFileNm, reviewPhotoEntityTestRs.getFileNm());
		
	}

	@Test
	@DisplayName("리뷰수정 사진추가")
	@Description("글만 작성한 리뷰에 사진을 추가하면 1점을 부여")
	void ReviewModAddPhotoTest(){
		//전처리
		ReviewInertTestOnlyText(); //텍스트만 존재하는 리뷰등록

		String modReviewCts = "리뷰내용 수정 테스트 입니다.";
		String modPhotoFileNm = "add_reviewImg.jpg";

		ReviewEntity reviewEntity = reviewService.findOneByReviewId(testReviewId);
		//리뷰내용 수정
		reviewEntity.setReviewCts(modReviewCts);

		String photo_uuid = UUID.randomUUID().toString();

		//리뷰사진 추가
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm(modPhotoFileNm)
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);

		reviewService.modReview(reviewEntity);

		ReviewEntity reviewEntityTestRs =  reviewService.findOneByReviewId(testReviewId);
		ReviewPhotoEntity reviewPhotoEntityTestRs =  reviewPhotoService.findOneByPhotoId(photo_uuid);
		
		assertNotNull(reviewEntityTestRs);
		assertEquals(modPhotoFileNm, reviewPhotoEntityTestRs.getFileNm());
		assertEquals(3, mileageService.findByUserId(userId_01).getPoint());
		
	}

	@Test
	@DisplayName("리뷰수정 내용추가")
	@Description("사진만 작성한 리뷰에 내용을 추가하면 1점을 부여")
	void ReviewModAddTextTest(){
		//전처리
		ReviewInertTestOnlyPhoto(); //텍스트만 존재하는 리뷰등록

		String modReviewCts = "리뷰내용 수정 테스트 입니다.";
		String modPhotoFileNm = "add_reviewImg.jpg";

		ReviewEntity reviewEntity = reviewService.findOneByReviewId(testReviewId);
		//리뷰내용 수정
		reviewEntity.setReviewCts(modReviewCts);

		String photo_uuid = UUID.randomUUID().toString();

		//리뷰사진 추가
		ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photo_uuid)
		.filePath("/img/test/")
		.fileNm(modPhotoFileNm)
		.build();

		reviewPhotoEntity.setReviewEntity(reviewEntity);
		reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);

		reviewService.modReview(reviewEntity);

		ReviewEntity reviewEntityTestRs =  reviewService.findOneByReviewId(testReviewId);
		ReviewPhotoEntity reviewPhotoEntityTestRs =  reviewPhotoService.findOneByPhotoId(photo_uuid);
		
		assertNotNull(reviewEntityTestRs);
		assertEquals(modPhotoFileNm, reviewPhotoEntityTestRs.getFileNm());
		assertEquals(3, mileageService.findByUserId(userId_01).getPoint());
		
	}

	@Test
	@DisplayName("리뷰수정 사진삭제")
	@Description("글과 사진이 있는 리뷰에서 사진을 모두 삭제하면 1점을 회수")
	void ReviewModDelPhotoTest(){
		//전처리
		ReviewInertTest(); //리뷰등록

		String modReviewCts = "리뷰내용 수정 테스트 입니다.";

		ReviewEntity reviewEntity = reviewService.findOneByReviewId(testReviewId);
		//리뷰내용 수정
		reviewEntity.setReviewCts(modReviewCts);
		reviewEntity.setReviewPhotoList(null);

		reviewService.modReview(reviewEntity);

		ReviewEntity reviewEntityTestRs =  reviewService.findOneByReviewId(testReviewId);
		
		assertNotNull(reviewEntityTestRs);
		assertEquals(modReviewCts, reviewEntityTestRs.getReviewCts());
		assertEquals(2, mileageService.findByUserId(userId_01).getPoint());
		
	}

	@Test
	@DisplayName("리뷰수정 내용 삭제")
	@Description("글과 사진이 있는 리뷰에서 내용을 삭제하면 1점을 회수")
	void ReviewModDelTextTest(){
		//전처리
		ReviewInertTest(); //리뷰등록


		ReviewEntity reviewEntity = reviewService.findOneByReviewId(testReviewId);
		//리뷰내용 삭제
		reviewEntity.setReviewCts(null);

		reviewService.modReview(reviewEntity);

		ReviewEntity reviewEntityTestRs =  reviewService.findOneByReviewId(testReviewId);
		
		assertNotNull(reviewEntityTestRs);
		assertEquals(2, mileageService.findByUserId(userId_01).getPoint());
		
	}



	@Test
	@DisplayName("리뷰삭제")
	@Description("리뷰를 작성했다가 삭제하면 해당 리뷰로 부여한 내용 점수와 보너스 점수는 회수")
	void ReviewDeleteTest(){
		//전처리
		ReviewInertTest(); //리뷰등록

		ReviewEntity reviewEntity = reviewService.findOneByReviewId(testReviewId);

		reviewService.deleteReview(reviewEntity);

		ReviewEntity reviewEntityTestRs = reviewService.findOneByReviewId(testReviewId);

		assertNull(reviewEntityTestRs);
		assertEquals(0, mileageService.findByUserId(userId_01).getPoint());
	}

}
