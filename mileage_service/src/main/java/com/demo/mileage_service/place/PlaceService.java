package com.demo.mileage_service.place;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaceService {
    
    @Autowired
    private PlaceRepository placeRepository;
    
    public void insertPlace(PlaceEntity placeEntity){
        placeRepository.save(placeEntity);
    }
    
    public Optional<PlaceEntity> findById(String placeId){
        return placeRepository.findById(placeId);
    }

}
