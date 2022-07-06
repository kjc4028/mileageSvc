package com.demo.mileage_service.mileage.hst;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageHstRepository extends JpaRepository<MileageHstEntity, String> {
    
    public MileageHstEntity findByReviewIdAndMileageCls(String reviewId, String mileageCls);
}
