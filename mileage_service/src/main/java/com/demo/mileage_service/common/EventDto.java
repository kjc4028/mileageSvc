package com.demo.mileage_service.common;

import lombok.Data;

@Data
public class EventDto {
    
    private String type;
    
    private String action;
    
    private String reviewId;
    
    private String content;
    
    private String[] attachedPhotoIds;
   
    private String userId;
    
    private String placeId;


}

