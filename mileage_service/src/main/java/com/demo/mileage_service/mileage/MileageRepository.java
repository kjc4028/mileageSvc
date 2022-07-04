package com.demo.mileage_service.mileage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MileageRepository extends JpaRepository<MileageEntity, Integer>{
    
    MileageEntity findByUserId(String userId);

}
