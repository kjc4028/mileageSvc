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

@Service
@Transactional
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MileageHstRepository mileageHstRepository; 

    @Autowired
    private MileageRepository mileageRepository;

    public void save(ReviewEntity revieweEntity){
        reviewRepository.save(revieweEntity);
    }

    //리뷰 등록
    public String reviewInsert(ReviewEntity revieweEntity){
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
        List<ReviewEntity> reviewList = reviewRepository.findByPlaceEntityAndUserId(revieweEntity.getPlaceEntity(), revieweEntity.getUserId());

        if(reviewList.size() > 0){
            return "duple";
        }

        String reviewId = UUID.randomUUID().toString();
        revieweEntity.setReviewId(reviewId);
        
        reviewRepository.save(revieweEntity);
        //최초등록여부 구분
        int reviewCnt = reviewRepository.findByReviewId(revieweEntity.getReviewId()).size();
        
        //사용자 마일리지 점수
        MileageEntity mileageEntity = mileageRepository.findByUserId(revieweEntity.getUserId());


        //최초등록
        if(reviewCnt == 0){
            MileageHstEntity mileageHstEntity = MileageHstEntity.builder()
            .reviewId(revieweEntity.getReviewId())
            .mileageCls("보너스점수")
            .actionDh(new Date())
            .pointProc(1)
            .userId(revieweEntity.getUserId())
            .build();
            
            MileageHstEntity mileageHstEntityForSave = mileageHstEntity;

            mileageHstEntityForSave.setTotalPoint(mileageEntity.getPoint() + mileageHstEntity.getPointProc());

            mileageHstRepository.save(mileageHstEntityForSave);
            
            mileageEntity.setPoint(mileageHstEntityForSave.getTotalPoint());
            mileageRepository.save(mileageEntity);
            //보너스 등록
        } else { //미최초등록

        }

        if(revieweEntity.getReviewCts() != null && revieweEntity.getReviewCts().length() > 0){
            MileageHstEntity mileageHstEntity = MileageHstEntity.builder()
            .reviewId(revieweEntity.getReviewId())
            .mileageCls("내용점수(텍스트)")
            .actionDh(new Date())
            .pointProc(1)
            .userId(revieweEntity.getUserId())
            .build();   
            
            MileageHstEntity mileageHstEntityForSave = mileageHstEntity;

            mileageHstEntityForSave.setTotalPoint(mileageEntity.getPoint() + mileageHstEntity.getPointProc());

            mileageHstRepository.save(mileageHstEntityForSave);
            
            mileageEntity.setPoint(mileageHstEntityForSave.getTotalPoint());
            mileageRepository.save(mileageEntity);            
        }

        if(revieweEntity.getReviewPhotoList() != null){
            MileageHstEntity mileageHstEntity = MileageHstEntity.builder()
            .reviewId(revieweEntity.getReviewId())
            .mileageCls("내용점수(사진)")
            .actionDh(new Date())
            .pointProc(1)
            .userId(revieweEntity.getUserId())
            .build();   
            
            MileageHstEntity mileageHstEntityForSave = mileageHstEntity;

            mileageHstEntityForSave.setTotalPoint(mileageEntity.getPoint() + mileageHstEntity.getPointProc());

            mileageHstRepository.save(mileageHstEntityForSave);
            
            mileageEntity.setPoint(mileageHstEntityForSave.getTotalPoint());
            mileageRepository.save(mileageEntity);                
        }

        return "reviewInsSucc";

    }

    public String modReview(ReviewEntity reviewEntity){
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

         if (!reviewEntity.getReviewCts().isEmpty() && existReviewEntity.getReviewCts().isEmpty()){
            System.out.println("변경사항 발생 내용: 내용 추가됨");
        } else if (reviewEntity.getReviewCts().isEmpty() && !existReviewEntity.getReviewCts().isEmpty()){
            System.out.println("변경사항 발생 내용: 내용 삭제됨");
        }
        //System.out.println(reviewEntity.getReviewPhotoList().isEmpty());
        //System.out.println(existReviewEntity.getReviewPhotoList().isEmpty());
        System.out.println(reviewEntity.getReviewPhotoList() == null);
        System.out.println(existReviewEntity.getReviewPhotoList() == null);


        if (!reviewEntity.getReviewPhotoList().isEmpty() && existReviewEntity.getReviewPhotoList().isEmpty()){
            System.out.println("변경사항 발생 내용: 사진 추가됨");
        } else if (reviewEntity.getReviewPhotoList().isEmpty() && !existReviewEntity.getReviewPhotoList().isEmpty()){
            System.out.println("변경사항 발생 내용: 사진 삭제됨");
        }

        reviewRepository.save(reviewEntity);
        return "미처리";

    }

    public void deleteReview(ReviewEntity reviewEntity){
        //리뷰삭제
            //사진삭제
            //리뷰삭제
            //마일리지 회수 이력
            //마일리지 회수
    }


}
