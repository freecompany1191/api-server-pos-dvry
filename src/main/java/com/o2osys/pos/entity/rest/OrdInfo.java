package com.o2osys.pos.entity.rest;

import lombok.Data;

/**
   @FileName  : OrdInfo.java
   @Description : 만나주문
   @author      : 주정
   @since       : 2018. 5. 18.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2018. 5. 18.    주정		           최초생성

 */
@Data
public class OrdInfo {
	
	public OrdInfo() {}
	
	/** 주문일련번호 */
    private String ordNo;

    /** 고객사_가맹점_코드 */
    private String posShopCode;

    /** 고객사_주문_코드 (주문일련번호) */
    private String posOrdCode;
    
    /** 고객사_주문_등록일 */
    private String posOrdDate;
    
    /** 주문_경로 (1:배민앱 2:배달통앱 3:요기요앱 9:기타  ) */
    private String ordPath;

    /** 주문_상태 (1: 배달접수, 2: 배차, 3: 출발, 4: 완료, 5: 취소, 6: 대기) */
    private String ordStatus;
    
    /** 고객 전화번호 */
    private String ordCuTel;

    /** 최종수정_일시 (YYYYMMDDHH24MISS) */
    private String modDate;

    /** 배차_일시 (YYYYMMDDHH24MISS) */
    private String cthDate;
    
    /** 배차_배송원_전화번호 */
    private String cthWkTel;
    
    /** 수주 가맹점 명 */
    private String cthStName;

    /** 출발_일시 (YYYYMMDDHH24MISS) */
    private String pickupDate;

    /** 배달대행_금액 */
    private long dvryAmt;

    /** 배달대행_할증_요금 */
    private long dvryExCharge;

    /** 배달대행_관리_가격 (건당 배송수수료) */
    private long dvryMngFee;
    
    /** 주문 메모 */
    private String ordMemo;
    
    /** 완료_일시 (YYYYMMDDHH24MISS) */
    private String endDate;
    
    /** 취소_일시 (YYYYMMDDHH24MISS) */
    private String cancelDate;
    
    /** 취소_내용 */
    private String cancelMemo;
    
    /** 주문_금액 */
    private long ordAmt;
    
    /** 지불_금액 (고객이 결제해야할 금액) */
    private long payAmt;
    
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
    
    /** 총_결제금액 (카드결제금액) */
    private long totalAmount;
    
    /** 배차_배송원_이름 */
    private String cthWkName;
    
    /** 배달_거리 (미터단위) */
    private long dvryDistance;
}
