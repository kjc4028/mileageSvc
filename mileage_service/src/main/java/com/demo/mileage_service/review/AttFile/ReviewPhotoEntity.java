package com.demo.mileage_service.review.AttFile;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="review_photo")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName ="reviewPhotoBuilder")
public class ReviewPhotoEntity {
    public ReviewPhotoEntity(){}
    @Id
    String photoId;

    //String reviewId;

    String filePath;

    String fineNm;
    
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewId", foreignKey = @ForeignKey(name = "reviewEntity", value = ConstraintMode.CONSTRAINT))
    private ReviewEntity reviewEntity;

    public static ReviewPhotoEntityBuilder builder(String id){
        return reviewPhotoBuilder().photoId(id);
    }
}
