package com.o2osys.pos.entity.rest;

import lombok.Data;

/**
   @FileName  : ConStatusInfo.java
   @Description : 상태변경 연동대상 목록
   @author      : KMS
   @since       : 2017. 11. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 11. 20.     KMS            최초생성

 */
@Data
public class ConStatusInfo {

    /** 연동_처리_구분 (1: 배차, 2: 출발, 3: 완료, 4: 취소, 5: 배차취소)  */
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
    /** POS_주문_코드 (주문일련번호) */
    private String posOrdCode;
    /** POS_주문_일자 (YYYYMMDD) */
    private String posOrdDate;
    /** 배차_일시 (YYYYMMDDHH24MISS) */
    private String cthDate;
    /** 배차_배송원_전화번호 */
    private String cthWkTel;
    /** 배차_배송원_이름 */
    private String cthWkName;
    /** 배차_배송원_도착_일시 (YYYYMMDDHH24MISS) - 2017-11-30 추가분 */
    private String cthWkArrDate;
    /** 출발_일시 (YYYYMMDDHH24MISS) */
    private String pickupDate;
    /** 완료_일시 (YYYYMMDDHH24MISS) */
    private String endDate;
    /** 배달대행_금액 */
    private String dvryAmt;
    /** 배달대행_할증_요금 */
    private String dvryExCharge;
    /** 배달대행_관리_가격 (건당 배송수수료) */
    private String dvryMngFee;
    /** 주문_금액 (총액) */
    private String ordAmt;
    /** 지불_금액 (총액) */
    private String payAmt;
    /** 결제_구분 (1: 도착지결제, 2: 선결제) */
    private String chargeType;
    /** 지불_구분 (1: 현금, 2: 카드) */
    private String payType;
    /** 카드_승인_번호 */
    private String cardApprNum;
    /** 승인_구분 (11: 신용승인, 12: 승인취소, 21: 현금영수증, 22: 영수증 취소) */
    private String tranType;
    /** 카드_번호 */
    private String cardNum;
    /** 매입사_이름 (카드사) */
    private String acquirerName;
    /** 가맹점_번호 (카드사 가맹점번호) */
    private String merchantNum;
    /** 총_결제금액 */
    private String totalAmount;
    /** 거래승인_일시 (YYMMDDHH24MISSW, W는 요일(0: 일요일)) - 2017-11-30 추가분 */
    private String approvalDate;
    /** 취소_일시 (YYYYMMDDHH24MISS) */
    private String cancelDate;
    /** 취소_내용 */
    private String cancelMemo;
    /** 최종수정_일시 (YYYYMMDDHH24MISS) */
    private String modDate;
    /** 연동_일련_번호 */
    private String conSeqNo;

    /** 토큰_인증_정보 (현재는 SKY POS만 출력함) */
    private String tokenAuthInfo;
}
