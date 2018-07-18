package com.o2osys.pos.entity.rest;

import java.util.ArrayList;

import lombok.Data;

/**
   @FileName  : ConInfo.java
   @Description : 연동대상 목록
   @author      : KMS
   @since       : 2017. 7. 24.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 24.     KMS            최초생성

 */
@Data
public class ConInfo {

    /** 연동_처리_구분 (1: 접수, 2: 수정, 3: 취소)  */
    private String conActType;
    /** POS_구분_코드 (SY_CODE = 'PT') */
    private String posTypeCd;
    /** 연동_URL */
    private String conUrl;
    /** 연동_데이터_1 (업체구분코드) */
    private String conData1;
    /** 연동_데이터_2 (인증키)  */
    private String conData2;
    /** 연동_데이터_3 (스카이포스 토큰생성 URL) */
    private String conData3;
    /** POS_가맹점_코드 */
    private String posShopCode;
    /** 주문_일련번호 */
    private String ordNo;
    /** 주문_고객_전화번호 */
    private String ordCuTel;
    /** 주문_고객_전화번호_2 (연락번호) */
    private String ordCuTel2;
    /** 주문_메모 */
    private String ordMemo;
    /** 테이크아웃_여부 */
    private String takeoutYn;
    /** 테이크아웃_일시 (YYYYMMDDHH24MISS. 테이크아웃_여부가 'Y'일 경우 전송) */
    private String takeoutDate;
    /** 준비_시간 (단위: 분, 조리시간) */
    private String readyTime;
    /** 도착_주소_1 (시/도) */
    private String eaAddr1;
    /** 도착_주소_2 (시/군/구) */
    private String eaAddr2;
    /** 도착_주소_3 (읍/면/동/리) */
    private String eaAddr3;
    /** 도착_주소_지번주소 (구주소, 전체 주소. 예: 서울특별시 구로구 신도림동 337) */
    private String eaAddrJb;
    /** 도착_주소_도로명주소 (신주소, 전체 주소. 예: 서울특별시 구로구 경인로 661) */
    private String eaAddrSt;
    /** 도착_주소_상세주소 (사용자입력 상세주소. 예: 푸르지오1차 104동 906호) */
    private String eaAddrEtc;
    /** 도착_위도_좌표 (Y좌표, LATITUDE) */
    private String eaLatY;
    /** 도착_경도_좌표 (X좌표, LONGITUDE) */
    private String eaLngX;
    /** 주문_금액 (총액) */
    private String ordAmt;
    /** 지불_금액 (총액) */
    private String payAmt;
    /** 결제_구분 (1: 도착지결제, 2: 선결제) */
    private String chargeType;
    /** 지불_구분 (1: 현금, 2: 카드) */
    private String payType;
    /** 프랜차이즈_구분_코드 */
    private String frTypeCode;
    /** 토큰_인증_정보 (현재는 SKY POS만 출력함) */
    private String tokenAuthInfo;
    /** 상품_이름 (표시 이름) */
    private String goodsNames;
    /** 상품_목록 */
    private ArrayList<Goods> goods;
}
