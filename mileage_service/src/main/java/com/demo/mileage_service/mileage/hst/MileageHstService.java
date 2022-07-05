package com.demo.mileage_service.mileage.hst;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mileage_service.mileage.MileageEntity;
import com.demo.mileage_service.mileage.MileageRepository;
import com.demo.mileage_service.review.ReviewEntity;

@Service
@Transactional
public class MileageHstService {
    
    @Autowired
    private MileageHstRepository mileageHstRepository;

    @Autowired
    private MileageRepository mileageRepository;

    public void decrementPoint(){

    }

    //이력 적재
    //포인트 갱신
    public void pointDecInc(ReviewEntity reviewEntity, String mileageClsStr, int pointProcNum){
        MileageHstEntity mileageHstEntity = MileageHstEntity.builder()
        .reviewId(reviewEntity.getReviewId())
        .mileageCls(mileageClsStr)
        .actionDh(new Date())
        .pointProc(pointProcNum)
        .userId(reviewEntity.getUserId())
        .build();
         
        //사용자 마일리지 점수
        MileageEntity mileageEntity = mileageRepository.findByUserId(reviewEntity.getUserId());

        MileageHstEntity mileageHstEntityForSave = mileageHstEntity;

        mileageHstEntityForSave.setTotalPoint(mileageEntity.getPoint() + mileageHstEntity.getPointProc());

        mileageHstRepository.save(mileageHstEntityForSave);
        
        mileageEntity.setPoint(mileageHstEntityForSave.getTotalPoint());

        mileageRepository.save(mileageEntity);
    }
}
