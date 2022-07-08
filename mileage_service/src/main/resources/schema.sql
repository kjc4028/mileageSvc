-- DROP DATABASE IF EXISTS mileagesvc_jckim;

-- CREATE DATABASE mileagesvc_jckim;

-- USE mileagesvc_jckim;

DROP TABLE if EXISTS review_photo;
DROP TABLE if EXISTS review;
DROP TABLE if EXISTS place;
DROP TABLE if EXISTS mileage;
DROP TABLE if EXISTS mileage_hst;

--마일리지 테이블 생성
CREATE TABLE `mileage` (
	`user_id` VARCHAR(255) NOT NULL,
	`point` INT NOT NULL,
	PRIMARY KEY (`user_id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

--마일리지 이력 테이블 생성
CREATE TABLE `mileage_hst` (
	`mileage_hst_seq` INT NOT NULL AUTO_INCREMENT,
	`action_dh` DATETIME(6) NULL DEFAULT NULL,
	`mileage_cls` VARCHAR(255) NULL DEFAULT NULL,
	`point_proc` INT NOT NULL,
	`review_id` VARCHAR(255) NULL DEFAULT NULL,
	`total_point` INT NOT NULL,
	`user_id` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`mileage_hst_seq`),
	INDEX `idx_review_id` (`review_id`),
	INDEX `idx_user_id` (`user_id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

--장소 테이블 생성
CREATE TABLE `place` (
	`place_id` VARCHAR(255) NOT NULL,
	`place_nm` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`place_id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

--리뷰 테이블 생성
CREATE TABLE `review` (
	`review_id` VARCHAR(255) NOT NULL,
	`review_cts` VARCHAR(255) NULL DEFAULT NULL,
	`user_id` VARCHAR(255) NULL DEFAULT NULL,
	`place_id` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`review_id`),
	INDEX `placeEntity` (`place_id`),
	CONSTRAINT `placeEntity` FOREIGN KEY (`place_id`) REFERENCES `place` (`place_id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;

--리뷰 사진 테이블 생성
CREATE TABLE `review_photo` (
	`photo_id` VARCHAR(255) NOT NULL,
	`file_path` VARCHAR(255) NULL DEFAULT NULL,
	`file_nm` VARCHAR(255) NULL DEFAULT NULL,
	`review_id` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`photo_id`),
	INDEX `reviewEntity` (`review_id`),
	CONSTRAINT `reviewEntity` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`)
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;