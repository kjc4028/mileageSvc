package com.demo.mileage_service.mileage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MileageService {
    
    @Autowired
    private MileageRepository mileageRepository;

    public MileageEntity findByUserId(String userId){
        return mileageRepository.findByUserId(userId);
    }

    public void insertMileageInfo(MileageEntity mileageEntity){
        mileageRepository.save(mileageEntity);
    }
}
