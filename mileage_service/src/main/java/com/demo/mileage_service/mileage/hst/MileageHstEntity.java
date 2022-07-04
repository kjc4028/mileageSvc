package com.demo.mileage_service.mileage.hst;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name="mileage_hst")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName ="mileageHstBuilder")
public class MileageHstEntity {
    public MileageHstEntity(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int mileageHstSeq;

    String reviewId;

    String mileageCls;

    Date actionDh;

    int pointProc;
    
    int totalPoint;

    String userId;

    public static MileageHstEntityBuilder builder(){
        return mileageHstBuilder();
    }
}