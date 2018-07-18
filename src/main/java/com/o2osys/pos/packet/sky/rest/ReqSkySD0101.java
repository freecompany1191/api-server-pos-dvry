package com.o2osys.pos.packet.sky.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.entity.rest.ConStatusInfo;

import lombok.Data;

/**
   @FileName  : ReqSkySD0101.java
   @ReqNum      : SD01_01_V01
   @Description : 만나주문 상태(1: 배차, 2: 출발, 3: 완료, 4: 취소, 5: 배차취소) 전송
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.   KMS            최초생성

 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
public class ReqSkySD0101 {

    public ReqSkySD0101() {
    }

    public ReqSkySD0101(ReqSkyHeader header, ReqSkySD0101Body body) {
        this.header = header;
        this.body = body;
    }

    @JsonProperty("header")
    private ReqSkyHeader header;

    @JsonProperty("body")
    private ReqSkySD0101Body body;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(Include.ALWAYS)
    public static class ReqSkySD0101Body {

        public ReqSkySD0101Body() {
        }

        public ReqSkySD0101Body(ConStatusInfo conStatus) {
            this.inOrdStatus = conStatus.getConActType();     //주문_상태 (1: 배차, 2: 출발, 3: 완료, 4: 취소, 5: 배차취소)
            this.inOrdNo = conStatus.getOrdNo();              //만나주문_일련번호
            this.inPosShopCode = conStatus.getPosShopCode();  //고객사_가맹점_코드
            this.inPosOrdCode = conStatus.getPosOrdCode();    //고객사_주문_코드 (주문일련번호)
            this.inPosOrdDate = conStatus.getPosOrdDate();    //고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우)
            this.inCthDate = conStatus.getCthDate();          //배차_일시 (YYYYMMDDHH24MISS)
            this.inCthWkTel = conStatus.getCthWkTel();        //배차_배송원_전화번호
            this.inCthWkName = conStatus.getCthWkName();      //배차_배송원_이름
            this.inCthWkArrDate = conStatus.getCthWkArrDate();//배차_배송원_도착_일시 (YYYYMMDDHH24MISS) - 2017-11-30 추가분
            this.inPickupDate = conStatus.getPickupDate();    //만나주문 상태(출발) 전송
            this.inEndDate = conStatus.getEndDate();          //완료_일시 (YYYYMMDDHH24MISS)
            this.inDvryAmt = conStatus.getDvryAmt();          //배달대행_금액
            this.inDvryExCharge = conStatus.getDvryExCharge();//배달대행_할증_요금
            this.inDvryMngFee = conStatus.getDvryMngFee();    //배달대행_관리_가격 (건당 배송수수료)
            this.inOrdAmt = conStatus.getOrdAmt();            //주문_금액
            this.inPayAmt = conStatus.getPayAmt();            //지불_금액 (고객이 결제해야할 금액)
            this.inChargeType = conStatus.getChargeType();    //결제_구분 (1: 도착지결제, 2: 선결제)
            this.inPayType = conStatus.getPayType();          //지불_구분 (1: 현금, 2: 카드)
            this.inCardApprNum = conStatus.getCardApprNum();  //카드_승인_번호
            this.inTranType = conStatus.getTranType();        //승인_구분 (11: 신용승인, 12: 승인취소, 21: 현금영수증, 22: 영수증 취소)
            this.inCardNum = conStatus.getCardNum();          //카드_번호
            this.inAcquirerName = conStatus.getAcquirerName();//매입사_이름 (카드사)
            this.inMerchantNum = conStatus.getMerchantNum();  //가맹점_번호 (카드사 가맹점번호)
            this.inTotalAmount = conStatus.getTotalAmount();  //총_결제금액 (카드결제금액)
            this.inApprovalDate = conStatus.getApprovalDate();//거래승인_일시 (YYMMDDHH24MISSW, W는 요일(0: 일요일)) - 2017-11-30 추가분
            this.inCancelDate = conStatus.getCancelDate();    //취소_일시 (YYYYMMDDHH24MISS)
            this.inCancelMemo = conStatus.getCancelMemo();    //취소_내용
            this.inModDate = conStatus.getModDate();          //최종수정_일시 (YYYYMMDDHH24MISS)
        }

        /** 만나주문 상태(배차) 전송 */
        /** 주문_상태 (1: 배차, 2: 출발, 3: 완료, 4: 취소, 5: 배차취소) */
        @JsonProperty("in_ORD_STATUS")
        private String inOrdStatus;

        /** 만나주문_일련번호 */
        @JsonProperty("in_ORD_NO")
        private String inOrdNo;

        /** 고객사_가맹점_코드 */
        @JsonProperty("in_POS_SHOP_CODE")
        private String inPosShopCode;

        /** 고객사_주문_코드 (주문일련번호) */
        @JsonProperty("in_POS_ORD_CODE")
        private String inPosOrdCode;

        /** 고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우) */
        @JsonProperty("in_POS_ORD_DATE")
        private String inPosOrdDate;

        /** 배차_일시 (YYYYMMDDHH24MISS) */
        @JsonProperty("in_CTH_DATE")
        private String inCthDate;

        /** 배차_배송원_전화번호 */
        @JsonProperty("in_CTH_WK_TEL")
        private String inCthWkTel;

        /** 배차_배송원_이름 */
        @JsonProperty("in_CTH_WK_NAME")
        private String inCthWkName;

        /* 2017-11-30 추가분 */
        /** 배차_배송원_도착_일시 (YYYYMMDDHH24MISS) */
        @JsonProperty("in_CTH_WK_ARR_DATE")
        private String inCthWkArrDate;

        /** 만나주문 상태(출발) 전송 */
        /** 출발_일시 (YYYYMMDDHH24MISS) */
        @JsonProperty("in_PICKUP_DATE")
        private String inPickupDate;

        /** 만나주문 상태(완료) 전송  */
        /** 완료_일시 (YYYYMMDDHH24MISS) */
        @JsonProperty("in_END_DATE")
        private String inEndDate;

        /** 배달대행_금액 */
        @JsonProperty("in_DVRY_AMT")
        private String inDvryAmt;

        /** 배달대행_할증_요금 */
        @JsonProperty("in_DVRY_EX_CHARGE")
        private String inDvryExCharge;

        /** 배달대행_관리_가격 (건당 배송수수료) */
        @JsonProperty("in_DVRY_MNG_FEE")
        private String inDvryMngFee;

        /** 주문_금액 */
        @JsonProperty("in_ORD_AMT")
        private String inOrdAmt;

        /** 지불_금액 (고객이 결제해야할 금액) */
        @JsonProperty("in_PAY_AMT")
        private String inPayAmt;

        /** 결제_구분 (1: 도착지결제, 2: 선결제) */
        @JsonProperty("in_CHARGE_TYPE")
        private String inChargeType;

        /** 지불_구분 (1: 현금, 2: 카드) */
        @JsonProperty("in_PAY_TYPE")
        private String inPayType;

        /** 카드_승인_번호 */
        @JsonProperty("in_CARD_APPR_NUM")
        private String inCardApprNum;

        /** 승인_구분 (11: 신용승인, 12: 승인취소, 21: 현금영수증, 22: 영수증 취소) */
        @JsonProperty("in_TRAN_TYPE")
        private String inTranType;

        /** 카드_번호 */
        @JsonProperty("in_CARD_NUM")
        private String inCardNum;

        /** 매입사_이름 (카드사) */
        @JsonProperty("in_ACQUIRER_NAME")
        private String inAcquirerName;

        /** 가맹점_번호 (카드사 가맹점번호) */
        @JsonProperty("in_MERCHANT_NUM")
        private String inMerchantNum;

        /** 총_결제금액 (카드결제금액) */
        @JsonProperty("in_TOTAL_AMOUNT")
        private String inTotalAmount;

        /* 2017-11-30 추가분 */
        /** 거래승인_일시 (YYMMDDHH24MISSW, W는 요일(0: 일요일)) */
        @JsonProperty("in_APPROVAL_DATE")
        private String inApprovalDate;

        /** 만나주문 상태(취소) 전송 */
        /** 취소_일시 (YYYYMMDDHH24MISS) */
        @JsonProperty("in_CANCEL_DATE")
        private String inCancelDate;

        /** 취소_내용 */
        @JsonProperty("in_CANCEL_MEMO")
        private String inCancelMemo;

        /** 만나주문 상태(배차취소) 전송 */
        /** 최종수정_일시 (YYYYMMDDHH24MISS) */
        @JsonProperty("in_MOD_DATE")
        private String inModDate;


    }

}
