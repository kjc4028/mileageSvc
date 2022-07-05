package com.demo.mileage_service.review.AttFile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.mileage_service.review.ReviewEntity;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhotoEntity, String> {
    
    List<ReviewPhotoEntity> findByReviewEntity(ReviewEntity reviewEntity);

    ReviewPhotoEntity findByPhotoId(String photoId);

}
