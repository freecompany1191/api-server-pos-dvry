package com.o2osys.pos.entity.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : GetOrdInfoPos.java
   @Description : 만나주문 조회 객체
   @ServiceCode : CB02_02
   @author      : KMS
   @since       : 2017. 9. 21.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 21.     KMS            최초생성

 */
@Data
public class GetOrdInfoPos {

    /** 주문_상태 (1: 배달접수, 2: 배차, 3: 출발, 4: 완료, 5: 취소, 6: 대기) */
    @JsonProperty("ORD_STATUS")
    private String ordStatus;

    /** 최종수정_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("MOD_DATE")
    private String modDate;

    /** 배차_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("CTH_DATE")
    private String cthDate;

    /** 배차_배송원_전화번호 */
    @JsonProperty("CTH_WK_TEL")
    private String cthWkTel;

    /** 출발_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("PICKUP_DATE")
    private String pickupDate;

    /** 완료_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("END_DATE")
    private String endDate;

    /** 취소_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("CANCEL_DATE")
    private String cancelDate;

    /** 취소_내용 */
    @JsonProperty("CANCEL_MEMO")
    private String cancelMemo;

    /** 배달대행_금액 */
    @JsonProperty("DVRY_AMT")
    private long dvryAmt;

    /** 배달대행_할증_요금 */
    @JsonProperty("DVRY_EX_CHARGE")
    private long dvryExCharge;

    /** 배달대행_관리_가격 (건당 배송수수료) */
    @JsonProperty("DVRY_MNG_FEE")
    private long dvryMngFee;

    /** 주문_금액 */
    @JsonProperty("ORD_AMT")
    private long ordAmt;

    /** 지불_금액 (고객이 결제해야할 금액) */
    @JsonProperty("PAY_AMT")
    private long payAmt;

    /** 결제_구분 (1: 도착지결제, 2: 선결제) */
    @JsonProperty("CHARGE_TYPE")
    private String chargeType;

    /** 지불_구분 (1: 현금, 2: 카드) */
    @JsonProperty("PAY_TYPE")
    private String payType;

    /** 카드_승인_번호 */
    @JsonProperty("CARD_APPR_NUM")
    private String cardApprNum;

    /** 승인_구분 (11: 신용승인, 12: 승인취소, 21: 현금영수증, 22: 영수증 취소) */
    @JsonProperty("TRAN_TYPE")
    private String tranType;

    /** 카드_번호 */
    @JsonProperty("CARD_NUM")
    private String cardNum;

    /** 매입사_이름 (카드사) */
    @JsonProperty("ACQUIRER_NAME")
    private String acquirerName;

    /** 총_결제금액 (카드결제금액) */
    @JsonProperty("TOTAL_AMOUNT")
    private long totalAmount;

    /** 배차_배송원_이름 */
    @JsonProperty("CTH_WK_NAME")
    private String cthWkName;

    /** 배달_거리 (미터단위) */
    @JsonProperty("DVRY_DISTANCE")
    private long dvryDistance;
}
