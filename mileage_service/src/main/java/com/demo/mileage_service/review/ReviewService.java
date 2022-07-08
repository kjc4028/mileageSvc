package com.demo.mileage_service.review;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mileage_service.common.Code;
import com.demo.mileage_service.mileage.hst.MileageHstEntity;
import com.demo.mileage_service.mileage.hst.MileageHstService;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.review.photo.ReviewPhotoEntity;
import com.demo.mileage_service.review.photo.ReviewPhotoRepository;
import com.demo.mileage_service.review.photo.ReviewPhotoService;

@Service
@Transactional
public class ReviewService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MileageHstService mileageHstService; 

    @Autowired
    private ReviewPhotoService reviewPhotoService;
    
    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    

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

        PlaceEntity placeIEntity = PlaceEntity.builder(reviewEntity.getPlaceEntity().getPlaceId()).build();
        //String reviewId = UUID.randomUUID().toString();
        //reviewEntity.setReviewId(reviewId);
        
        

        //최초등록여부 구분
        int reviewCnt = (reviewRepository.findByPlaceEntity(placeIEntity)).size();
        
        //최초등록
        if(reviewCnt == 0){
            mileageHstService.pointDecInc(reviewEntity, Code.BNS_ACC.getMsg(), 1);
            //보너스 등록
        }

        if(reviewEntity.getReviewCts() != null && reviewEntity.getReviewCts().length() > 0){
            mileageHstService.pointDecInc(reviewEntity, Code.RV_CT_ACC.getMsg(), 1);        
        }

        if(reviewEntity.getReviewPhotoList() != null){
            mileageHstService.pointDecInc(reviewEntity, Code.RV_PT_ACC.getMsg(), 1);                
        }

        reviewRepository.save(reviewEntity);

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
        StringBuilder resultMsg = new StringBuilder();
         if (reviewEntity.getReviewCts() != null && reviewEntity.getReviewCts().length()>0
            && existReviewEntity.getReviewCts() == null){
            logger.debug("변경사항 발생 내용: 내용 추가됨");
            resultMsg.append(Code.RV_CT_ACC.getMsg() + " ");
            mileageHstService.pointDecInc(reviewEntity, Code.RV_CT_ACC.getMsg(), 1);   
            //포인트 이력 증감 함수
        } else if (reviewEntity.getReviewCts() == null 
            && existReviewEntity.getReviewCts() != null && existReviewEntity.getReviewCts().length()>0){
            logger.debug("변경사항 발생 내용: 내용 삭제됨");
            resultMsg.append(Code.RV_CT_DED.getMsg() + " ");
            mileageHstService.pointDecInc(reviewEntity, Code.RV_CT_DED.getMsg(), -1);   
        }
        
        //reviewEntity와 existReviewEntity 차이
        if (reviewEntity.getReviewPhotoList() != null && reviewEntity.getReviewPhotoList().size()>0
            && existReviewEntity.getReviewPhotoList().isEmpty()){
            logger.debug("변경사항 발생 내용: 사진 추가됨");
            resultMsg.append(Code.RV_PT_ACC.getMsg() + " ");
            mileageHstService.pointDecInc(reviewEntity, Code.RV_PT_ACC.getMsg(), 1);   
        } else if ((reviewEntity.getReviewPhotoList() == null || reviewEntity.getReviewPhotoList().size() == 0)
        && !existReviewEntity.getReviewPhotoList().isEmpty()){
            logger.debug("변경사항 발생 내용: 사진 삭제됨");
            resultMsg.append(Code.RV_PT_DED.getMsg() + " ");
            mileageHstService.pointDecInc(reviewEntity, Code.RV_PT_DED.getMsg(), -1);   
        }

        reviewPhotoRepository.deleteByReviewEntity(reviewEntity);        
        reviewRepository.save(reviewEntity);

        return resultMsg.toString();

    }

    public String deleteReview(ReviewEntity reviewEntity){
        //리뷰삭제
            //보너스포인트 적립여부 확인 필요    
            //사진삭제
            //리뷰삭제
            //마일리지 회수 이력
            //마일리지 회수
            StringBuilder resultMsg = new StringBuilder();
            MileageHstEntity mileageHstEntity = mileageHstService.findByReviewIdAndMileageCls(reviewEntity.getReviewId(),Code.BNS_ACC.getMsg());
            

            ReviewEntity reviewEntityInfo = reviewRepository.findOneByReviewId(reviewEntity.getReviewId());

            if(mileageHstEntity != null){
                resultMsg.append(Code.RV_RM_BNS_DED.getMsg() + " ");
                mileageHstService.pointDecInc(reviewEntityInfo, Code.RV_RM_BNS_DED.getMsg(), -1);
            }

            List<ReviewPhotoEntity> photoList = null;
            if(reviewEntityInfo.getReviewPhotoList() != null){
                photoList = reviewEntityInfo.getReviewPhotoList();
            }
            
            if(photoList != null && !photoList.isEmpty()){
                resultMsg.append(Code.RV_RM_PT_DED.getMsg() + " ");
                mileageHstService.pointDecInc(reviewEntityInfo, Code.RV_RM_PT_DED.getMsg(), -1);
                for (ReviewPhotoEntity reviewPhotoEntity : photoList) {
                    ReviewPhotoEntity deletePhoto = reviewPhotoService.findOneByPhotoId(reviewPhotoEntity.getPhotoId());
                    reviewPhotoService.deletePhoto(deletePhoto);        
                }
            }
            
            resultMsg.append(Code.RV_RM_CT_DED.getMsg() + " ");
            mileageHstService.pointDecInc(reviewEntityInfo, Code.RV_RM_CT_DED.getMsg(), -1);
            reviewRepository.delete(reviewEntityInfo);

            return resultMsg.toString();
            
    }


}
