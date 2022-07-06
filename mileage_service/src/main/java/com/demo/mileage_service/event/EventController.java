package com.demo.mileage_service.event;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mileage_service.common.EventDto;
import com.demo.mileage_service.common.Message;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.review.ReviewEntity;
import com.demo.mileage_service.review.ReviewService;
import com.demo.mileage_service.review.AttFile.ReviewPhotoEntity;

@RestController
public class EventController {
    
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/event")
    @ResponseBody
    public ResponseEntity<Message> eventCall(@RequestBody EventDto eventDto){
        System.out.println(">>>>>");
        System.out.println(eventDto.getAttachedPhotoIds());
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        String[] photoArr = eventDto.getAttachedPhotoIds();
        List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();
        System.out.println(photoArr[0]); 
        PlaceEntity placeEntity = PlaceEntity.builder(eventDto.getPlaceId()).build();


        

        ReviewEntity reviewEntity = ReviewEntity.builder(eventDto.getReviewId())
                                    .reviewCts(eventDto.getContent())
                                    .reviewPhotoList(new ArrayList<>())
                                    .userId(eventDto.getUserId())
                                    .placeEntity(placeEntity)
                                    .build();

        for(String photoId : photoArr){
            ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photoId).build();
            reviewPhotoEntity.setReviewEntity(reviewEntity);
            //reviewPhotoList.add(reviewPhotoEntity);
            reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
        }

        if(eventDto.getType().equals("REVIEW")){
            if(eventDto.getAction().equals("ADD")){
                reviewService.reviewInsert(reviewEntity);
            } else if(eventDto.getAction().equals("MOD")) {
                reviewService.modReview(reviewEntity);
            } else if(eventDto.getAction().equals("DELETE")){
                reviewService.deleteReview(reviewEntity);
            }
        }


        message.setData("data");
        message.setMessage("message");
        message.setStatus(HttpStatus.OK);
        
        return new ResponseEntity<Message>(message, headers, HttpStatus.OK);
    }
}
