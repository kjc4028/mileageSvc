package com.demo.mileage_service.mileage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="mileage")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName ="mileageBuilder")
public class MileageEntity {
    public MileageEntity(){}
    @Id
    String userId;

    int point;
    
    public static MileageEntityBuilder builder(String id){
        return mileageBuilder().userId(id);
    }
}
