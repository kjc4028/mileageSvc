package com.demo.mileage_service.event;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mileage_service.common.EventDto;
import com.demo.mileage_service.common.Message;
import com.demo.mileage_service.review.ReviewEntity;
import com.demo.mileage_service.review.ReviewService;

@RestController
public class EventController {
    
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/event")
    @ResponseBody
    public ResponseEntity<Message> eventCall(EventDto eventDto){

        HttpHeaders headers = new HttpHeaders();
        Message message = new Message();

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setData("data");
        message.setMessage("message");
        message.setStatus(HttpStatus.OK);
        
        return new ResponseEntity<Message>(message, headers, HttpStatus.OK);
    }
}
