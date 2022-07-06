package com.demo.mileage_service.common;

import java.util.List;

import lombok.Data;

@Data
public class EventDto {
    
    private String type;
    
    private String action;
    
    private String reviewId;
    
    private String content;
    
    private List<String> attachedPhotoIds;
   
    private String userId;
    
    private String placeId;


}

