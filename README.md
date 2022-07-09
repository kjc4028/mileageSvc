# mileage_service

#### 리뷰작성에 따라서 마일리지 적립/회수를 처리하는 프로젝트 입니다.
---
### 프로젝트 사용기술
* java 11
* mysql 8.0.29
* springboot 2.6.10
* gradle 7.4.1
* spring-data-jpa
---
### DB Schema 정보
* Schema DDL 위치 : https://github.com/kjc4028/mileage_service/blob/master/mileage_service/src/main/resources/schema.sql
* 스키마구조(이미지)
<img src="https://github.com/kjc4028/mileage_service/blob/master/mileage_service/src/main/resources/static/mileageService_schema_structure%20.png" alt="스키마 구조 이미지 파일" width="500"/>

---

### 프로젝트 실행방법
mysql 접속하여 데이터베이스 생성
프로젝트 내에 설정한 데이터베이스명은 "mileagesvc_jckim"으로 아래 DDL 수행

``` 
CREATE DATABASE mileagesvc_jckim;
```
프로젝트 소스 git pull
https://github.com/kjc4028/mileage_service.git

application.properties 파일 내 DB 연결정보 변경
```
spring.datasource.url=jdbc:mysql://localhost:3306/mileagesvc_jckim
spring.datasource.username=root
spring.datasource.password=1234
```

#### 주의! 프로젝트 구동시 기존 데이터 삭제 후 DDL 및 기본 테스트 데이터는 자동 생성
gradle 명령어로 실행
```
{프로젝트경로}/gradlew.bat bootRun -D org.gradle.java.home="jdk 11버전 경로"
예) Windows 환경에서 실행 시 : {프로젝트경로}/gradlew.bat bootRun -D org.gradle.java.home="C:\Program Files\Java\java-11-openjdk-11.0.13.8-1.windows.redhat.x86_64"
```
---
### API 명세
* 리뷰 등록/수정/삭제
```
POST /event
jsonData
{
"type": "REVIEW", /*리뷰 이벤트로 고정 */
"action": "이벤트구분", /* "ADD", "MOD", "DELETE" */
"reviewId": "리뷰아이디",
"content": "리뷰내용",
"attachedPhotoIds": ["사진아이디1", "사진아이디2"],
"userId": "사용자아이디",
"placeId": "장소아이디"
}
```
* 사용자 마일리지 조회
```
GET /mileage/{userId}
```

* 마일리지 가입
```
POST /mileage/join
jsonData : {"userId" : "user01"}
```
* 장소 등록
```
POST /place
jsonData : {"placeNm" : "장소명"}
```
