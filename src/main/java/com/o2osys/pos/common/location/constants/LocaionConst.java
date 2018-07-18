package com.o2osys.pos.common.location.constants;

/**
   @FileName  : LocaionConst.java
   @Description : 주소검색 상수
   @author      : KMS
   @since       : 2017. 7. 26.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 26.     KMS            최초생성

 */
public interface LocaionConst {

    /** REST 응답 코드 */
    interface RES_CODE{
        /** 정상(0000) */
        final String OK = "0000";
        /** 처리거절(처리불가능 상황)(0002) */
        final String FAIL = "0002";
        /** 잘못된 요청값(요청값 누락)(0003) */
        final String REQ_ERROR="0003";
        /** 시스템 오류(정의되지 않은 오류)(9999) */
        final String SYSTEM_ERROR="9999";
    }

    /** REST 응답 코드 */
    interface RES_CODE_NAME{
        /** 정상(0000) */
        final String OK = "정상";
        /** 처리거절(처리불가능 상황)(0002) */
        final String FAIL = "처리거절";
        /** 잘못된 요청값(요청값 누락)(0003) */
        final String REQ_ERROR="잘못된 요청값(요청값 누락)";
        /** 시스템 오류(정의되지 않은 오류)(9999) */
        final String SYSTEM_ERROR="시스템 오류(정의되지 않은 오류)";
    }

}
