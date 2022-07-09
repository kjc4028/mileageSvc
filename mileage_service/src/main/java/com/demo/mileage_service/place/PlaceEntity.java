package com.demo.mileage_service.place;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.demo.mileage_service.review.ReviewEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="place")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName ="placeEntityBuilder")
public class PlaceEntity {
    public PlaceEntity(){}
    @Id
    String placeId;

    String placeNm;

    @OneToMany(mappedBy = "placeEntity")
    private List<ReviewEntity> reviewEntityList;

    public static PlaceEntityBuilder builder(String id){
        return placeEntityBuilder().placeId(id);
    }
}
