package com.demo.mileage_service.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.mileage_service.place.PlaceEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {

    public ReviewEntity findOneByReviewId(String reviewId);

    public List<ReviewEntity> findByReviewId(String reviewId);
    
    public List<ReviewEntity> findByPlaceEntity(PlaceEntity placeId);
    
    public List<ReviewEntity> findByPlaceEntityAndUserId(PlaceEntity placeEntity, String userId);


}
