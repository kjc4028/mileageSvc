package com.demo.mileage_service.common;

public enum Code {
    //acc ded
    BNS_ACC("보너스 점수 적립"),
    
    RV_RM_BNS_DED("리뷰삭제 보너스 점수 회수"),
    RV_RM_PT_DED("리뷰삭제(사진) 점수 회수"),
    RV_RM_CT_DED("리뷰삭제(내용) 점수 회수"),
    
    RV_PT_DED("내용점수(사진) 회수"),
    RV_PT_ACC("내용점수(사진) 적립"),
    
    RV_CT_DED("내용점수(내용) 회수"),
    RV_CT_ACC("내용점수(내용) 적립");

    private String msg;

    Code(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

}
