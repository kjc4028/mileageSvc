package com.demo.mileage_service.event;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mileage_service.common.EventDto;
import com.demo.mileage_service.common.Message;
import com.demo.mileage_service.mileage.MileageEntity;
import com.demo.mileage_service.mileage.MileageService;
import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.place.PlaceService;
import com.demo.mileage_service.review.ReviewEntity;
import com.demo.mileage_service.review.ReviewService;
import com.demo.mileage_service.review.photo.ReviewPhotoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EventController {
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private MileageService mileageService;

    @Autowired
    private PlaceService placeService;

    @PostMapping("/event")
    @ResponseBody
    public ResponseEntity<Message> eventCall(@RequestBody EventDto eventDto){
        
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        HttpStatus httpStatus = HttpStatus.OK;
        String resultMsg = "";
        String proccessMsg = "";

        List<String> photoArr = new ArrayList<String>();
        if(eventDto.getAttachedPhotoIds() != null){
            photoArr = eventDto.getAttachedPhotoIds();
        }

        PlaceEntity placeEntity = PlaceEntity.builder(eventDto.getPlaceId()).build();

        ReviewEntity reviewEntity = ReviewEntity.builder(eventDto.getReviewId())
                                    .reviewCts(eventDto.getContent())
                                    .userId(eventDto.getUserId())
                                    .reviewPhotoList(new ArrayList<>())
                                    .placeEntity(placeEntity)
                                    .build();

        for(String photoId : photoArr){
            ReviewPhotoEntity reviewPhotoEntity = ReviewPhotoEntity.builder(photoId).build();
            reviewPhotoEntity.setReviewEntity(reviewEntity);
            reviewEntity.getReviewPhotoList().add(reviewPhotoEntity);
        }

        if(eventDto.getType().equals("REVIEW")){
            if(eventDto.getAction().equals("ADD")){
                resultMsg = reviewService.reviewInsert(reviewEntity);
                if(resultMsg.equals("reviewInsSucc")){
                    proccessMsg = "?????? ?????? ??????";

                } else if(resultMsg.equals("duple")){
                    proccessMsg = "?????? ?????? ?????? ?????? ?????? ??????";
                }
            } else if(eventDto.getAction().equals("MOD")) {
                resultMsg = reviewService.modReview(reviewEntity);
                proccessMsg = "?????? ?????? ??????";
            } else if(eventDto.getAction().equals("DELETE")){
                resultMsg = reviewService.deleteReview(reviewEntity);
                proccessMsg = "?????? ?????? ??????";
            }
        }

        message.setData(resultMsg);
        message.setMessage(proccessMsg);
        message.setStatus(httpStatus);
        
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<Message>(message, headers, httpStatus);
    }

    @GetMapping("/mileage/{userId}")
    @ResponseBody
    public ResponseEntity<Message> selectMileage(EventDto eventDto){
        
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        ObjectMapper om = new ObjectMapper();
        
        String proccessMsg = "";

        MileageEntity mileageEntity = mileageService.findByUserId(eventDto.getUserId());
        
        resMap = om.convertValue(mileageEntity, HashMap.class);

        try {
            proccessMsg = "????????????";
        } catch (Exception e) {
            proccessMsg = "?????????????????? ?????? ??????";
        }

        message.setData(resMap);
        message.setMessage(proccessMsg);
        message.setStatus(httpStatus);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<Message>(message, headers, httpStatus);
    }

    @PostMapping("/mileage/join")
    @ResponseBody
    public ResponseEntity<Message> joinMileage(@RequestBody EventDto eventDto){
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        ObjectMapper om = new ObjectMapper();
        String proccessMsg = "";
        String userId = "";

        userId = eventDto.getUserId();

        if(userId == null || userId.isEmpty()){
            proccessMsg = "?????? ??? ???????????? ???????????????.";
        } else{
            MileageEntity mileageEntity = MileageEntity.builder(eventDto.getUserId()).point(0).build();

            MileageEntity dupleMember = mileageService.findByUserId(userId);
            if(dupleMember != null){
                proccessMsg = "?????? ????????? ????????? ?????????.";
            } else {
                mileageService.insertMileageInfo(mileageEntity);
                proccessMsg = "???????????? ?????? ??????";
                resMap = om.convertValue(mileageEntity, HashMap.class);
            }
        }

        message.setData(resMap);
        message.setMessage(proccessMsg);
        message.setStatus(httpStatus);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<Message>(message, headers, httpStatus);
    }

    @PostMapping("/place")
    @ResponseBody
    public ResponseEntity<Message> insertPlace(@RequestBody PlaceEntity placeDto){
        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();
        HttpStatus httpStatus = HttpStatus.OK;
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        ObjectMapper om = new ObjectMapper();
        String proccessMsg = "";


        String uuid = UUID.randomUUID().toString();

        if(placeDto.getPlaceNm() == null){
            proccessMsg = "????????? ????????? ???????????????.";
        } else {
            PlaceEntity placeEntity = PlaceEntity.builder(uuid).placeNm(placeDto.getPlaceNm()).build();
            
            placeService.insertPlace(placeEntity);
            proccessMsg = "???????????? ??????";
            resMap = om.convertValue(placeEntity, HashMap.class);
        }


        message.setData(resMap);
        message.setMessage(proccessMsg);
        message.setStatus(httpStatus);

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<Message>(message, headers, httpStatus);
    }

}
