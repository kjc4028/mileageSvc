package com.demo.mileage_service.mileage.hst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MileageHstRepository extends JpaRepository<MileageHstEntity, String> {
    
    // @Query("SELECT distinct FIRST_VALUE(total_point) OVER(ORDER BY action_dh DESC) from mileage_hst")
    // int lastestPoint();

}
