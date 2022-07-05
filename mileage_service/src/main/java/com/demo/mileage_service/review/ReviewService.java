package com.demo.mileage_service.review;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mileage_service.mileage.MileageEntity;
import com.demo.mileage_service.mileage.MileageRepository;
import com.demo.mileage_service.mileage.hst.MileageHstEntity;
import com.demo.mileage_service.mileage.hst.MileageHstRepository;
import com.demo.mileage_service.mileage.hst.MileageHstService;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.place.PlaceService;
import com.demo.mileage_service.review.AttFile.ReviewPhotoEntity;
import com.demo.mileage_service.review.AttFile.ReviewPhotoService;

@Service
@Transactional
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MileageHstService mileageHstService; 

    @Autowired
    private ReviewPhotoService reviewPhotoService;

    public ReviewEntity findOneByReviewId(String reviewId){
       return reviewRepository.findOneByReviewId(reviewId);
    }

    public void save(ReviewEntity reviewEntity){
        reviewRepository.save(reviewEntity);
    }

    //리뷰 등록
    public String reviewInsert(ReviewEntity reviewEntity){
        //중복등록여부
        //리뷰저장
        //리뷰포토등록
        //--마일리지계산
        //최초등록구분하여 보너스점수 지급
        //마일리지이력등록
        //내용점수(텍스트)
        //마일리지이력등록
        //내용점수(사진)
        //마일리지이력등록
        //마일리지최종반영


        //중복등록방지
        List<ReviewEntity> reviewList = reviewRepository.findByPlaceEntityAndUserId(reviewEntity.getPlaceEntity(), reviewEntity.getUserId());

        if(reviewList.size() > 0){
            return "duple";
        }

        String reviewId = UUID.randomUUID().toString();
        reviewEntity.setReviewId(reviewId);
        
        reviewRepository.save(reviewEntity);

        //최초등록여부 구분
        int reviewCnt = reviewRepository.findByReviewId(reviewEntity.getReviewId()).size();
        
        //최초등록
        if(reviewCnt == 0){
            mileageHstService.pointDecInc(reviewEntity, "보너스점수 적립", 1);
            //보너스 등록
        }

        if(reviewEntity.getReviewCts() != null && reviewEntity.getReviewCts().length() > 0){
            mileageHstService.pointDecInc(reviewEntity, "내용점수(텍스트) 적립", 1);        
        }

        if(reviewEntity.getReviewPhotoList() != null){
            mileageHstService.pointDecInc(reviewEntity, "내용점수(사진) 적립", 1);                
        }

        return "reviewInsSucc";

    }

    public void modReview(ReviewEntity reviewEntity){
        //리뷰수정
        //기존리뷰와 수정리뷰비교(글자, 사진)
            //기존과 동일한 경우
            //기존내용 없었는데 생기는 경우
                //마일리지 적립 이력
                //마일리지 적립
            //기존내용이 있었는데 없어지는 경우
                //마일리지 회수 이력
                //마일리지 회수
        ReviewEntity existReviewEntity = reviewRepository.findOneByReviewId(reviewEntity.getReviewId());

         if (reviewEntity.getReviewCts() != null 
            && existReviewEntity.getReviewCts() == null){
            System.out.println("변경사항 발생 내용: 내용 추가됨");
            mileageHstService.pointDecInc(reviewEntity, "내용점수(텍스트) 적립", 1);   
            //포인트 이력 증감 함수
        } else if (reviewEntity.getReviewCts() == null 
            && existReviewEntity.getReviewCts() != null){
            System.out.println("변경사항 발생 내용: 내용 삭제됨");
            mileageHstService.pointDecInc(reviewEntity, "내용점수(텍스트) 차감", -1);   
        }
        
        //reviewEntity와 existReviewEntity 차이
        if (reviewEntity.getReviewPhotoList() != null 
            && existReviewEntity.getReviewPhotoList().isEmpty()){
            System.out.println("변경사항 발생 내용: 사진 추가됨");
            mileageHstService.pointDecInc(reviewEntity, "내용점수(사진) 적립", 1);   
        } else if (reviewEntity.getReviewPhotoList() == null
            && !existReviewEntity.getReviewPhotoList().isEmpty()){
            System.out.println("변경사항 발생 내용: 사진 삭제됨");
            mileageHstService.pointDecInc(reviewEntity, "내용점수(사진) 차감", -1);   
        }

        reviewRepository.save(reviewEntity);

    }

    public void deleteReview(ReviewEntity reviewEntity){
        //리뷰삭제
            //사진삭제
            //리뷰삭제
            //마일리지 회수 이력
            //마일리지 회수
            ReviewEntity reviewEntityInfo = reviewRepository.findOneByReviewId(reviewEntity.getReviewId());
            List<ReviewPhotoEntity> photoList = null;
            if(reviewEntityInfo.getReviewPhotoList() != null){
                photoList = reviewEntityInfo.getReviewPhotoList();
            }
            
            

            if(photoList != null && !photoList.isEmpty()){
                mileageHstService.pointDecInc(reviewEntityInfo, "리뷰삭제 회수", -1);
                for (ReviewPhotoEntity reviewPhotoEntity : photoList) {
                    ReviewPhotoEntity deletePhoto = reviewPhotoService.findOneByPhotoId(reviewPhotoEntity.getPhotoId());
                    reviewPhotoService.deletePhoto(deletePhoto);        
                }
            }

            mileageHstService.pointDecInc(reviewEntityInfo, "리뷰삭제 회수", -1);
            reviewRepository.delete(reviewEntityInfo);


            
    }


}
