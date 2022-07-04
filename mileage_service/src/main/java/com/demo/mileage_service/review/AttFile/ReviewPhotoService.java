package com.demo.mileage_service.review.AttFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewPhotoService {
    
    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    
}
