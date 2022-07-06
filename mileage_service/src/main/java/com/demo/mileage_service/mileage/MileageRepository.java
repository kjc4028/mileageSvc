package com.demo.mileage_service.mileage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageRepository extends JpaRepository<MileageEntity, Integer>{
    
    MileageEntity findByUserId(String userId);

}
