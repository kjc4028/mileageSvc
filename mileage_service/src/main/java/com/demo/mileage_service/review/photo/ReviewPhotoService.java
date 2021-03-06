package com.demo.mileage_service.review.photo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewPhotoService {
    
    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    public ReviewPhotoEntity findOneByPhotoId(String photoId){
        return reviewPhotoRepository.findByPhotoId(photoId);

    }

    public void deletePhoto(ReviewPhotoEntity reviewPhotoEntity){
        
        reviewPhotoRepository.delete(reviewPhotoEntity);
    }

    public List<ReviewPhotoEntity> findByReviewId(ReviewPhotoEntity reviewPhotoEntity){
        
        return reviewPhotoRepository.findByReviewEntity(reviewPhotoEntity.getReviewEntity());
    }

    public void deleteReviewPhotoEntityByReviewId(String reviewId){
        reviewPhotoRepository.deleteReviewPhotoEntityByReviewId(reviewId);
    }
    
}
