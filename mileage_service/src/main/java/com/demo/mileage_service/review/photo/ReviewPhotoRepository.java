package com.demo.mileage_service.review.photo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.mileage_service.review.ReviewEntity;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhotoEntity, String> {
    
    List<ReviewPhotoEntity> findByReviewEntity(ReviewEntity reviewEntity);

    ReviewPhotoEntity findByPhotoId(String photoId);

    @Modifying
    @Query(value = "delete from reviewPhotoEntity rp where rp.reviewId = ?1", nativeQuery = true)
    void deleteReviewPhotoEntityByReviewId(String reviewId);

    void deleteByReviewEntity(ReviewEntity reviewEntity);

}
