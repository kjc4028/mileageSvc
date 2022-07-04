package com.demo.mileage_service.review;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.demo.mileage_service.place.PlaceEntity;
import com.demo.mileage_service.review.AttFile.ReviewPhotoEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="review")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName ="reviewEntityBuilder")
public class ReviewEntity {

    public  ReviewEntity(){}   


    @Id
    String reviewId;

    //String place_id;

    String reviewCts;

    String userId;

    @OneToMany(mappedBy = "reviewEntity")
    private List<ReviewPhotoEntity> reviewPhotoList = new ArrayList<ReviewPhotoEntity>();

    @ManyToOne
    @JoinColumn(name="placeId", foreignKey = @ForeignKey(name = "placeEntity", value = ConstraintMode.CONSTRAINT))
    private PlaceEntity placeEntity;


    public static ReviewEntityBuilder builder(String id){
        return reviewEntityBuilder().reviewId(id);
    }
}
