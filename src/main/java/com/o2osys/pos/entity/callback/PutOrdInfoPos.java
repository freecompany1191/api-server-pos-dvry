package com.o2osys.pos.entity.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.common.location.packet.Location;
import com.o2osys.pos.packet.callback.ReqPosMessage.ReqPosMsgBody;

import lombok.Data;

/**
   @FileName  : OrdInfoPos.java
   @Description : 배달요청 주문 전송 (배달요청, 주문수정, 주문취소) 처리 객체
   @ServiceCode : CB02_01
   @author      : KMS
   @since       : 2017. 9. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 20.     KMS            최초생성

 */
@Data
public class PutOrdInfoPos {

    public PutOrdInfoPos() {
    }


    public PutOrdInfoPos(ReqPosMsgBody body, String posTypeCode, Location location) {
        this.inActType = body.getInActType();
        this.inPosTypeCd = posTypeCode;
        this.inPosShopCode = body.getInPosShopCode();
        this.inPosOrdCode = body.getInPosOrdCode();
        this.inPosOrdDate = body.getInPosOrdDate();
        this.inOrdCuTel = body.getInOrdCuTel();
        this.inOrdCuTel2 = body.getInOrdCuTel2();
        this.inOrdMemo = body.getInOrdMemo();
        this.inReadyTime = body.getInReadyTime();
        this.location = location;
        this.inOrdAmt = body.getInOrdAmt();
        this.inPayAmt = body.getInPayAmt();
        this.inChargeType = body.getInChargeType();
        this.inPayType = body.getInPayType();
        this.inGoodsNames = body.getInGoodsNames();
        this.inCancelMemo = body.getInCancelMemo();
    }

    /** 처리_구분 (1: 배달요청, 2: 주문수정, 3: 주문취소) */
    @JsonProperty("in_ACT_TYPE")
    private String inActType;

    /** POS_구분_코드 (SY_CODE = 'PT') */
    @JsonProperty("in_POS_TYPE_CD")
    private String inPosTypeCd;

    /** 고객사_가맹점_코드 */
    @JsonProperty("in_POS_SHOP_CODE")
    private String inPosShopCode;

    /** 고객사_주문_코드 (주문일련번호) */
    @JsonProperty("in_POS_ORD_CODE")
    private String inPosOrdCode;

    /** 고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우) */
    @JsonProperty("in_POS_ORD_DATE")
    private String inPosOrdDate;

    /** 주문_고객_전화번호 */
    @JsonProperty("in_ORD_CU_TEL")
    private String inOrdCuTel;

    /** 주문_고객_전화번호_2 (수령고객. 주문고객과 수령고객이 같을 경우 주문_고객_전화번호로 전달) */
    @JsonProperty("in_ORD_CU_TEL_2")
    private String inOrdCuTel2;

    /** 주문_메모 */
    @JsonProperty("in_ORD_MEMO")
    private String inOrdMemo;

    /** 준비_시간 (단위: 분, 조리시간) */
    @JsonProperty("in_READY_TIME")
    private int inReadyTime;

    /** 카카오 주소 API 검색을 통해 셋팅된 주소 */
    @JsonProperty("LOCATION")
    private Location location;

    /** 주문_금액 */
    @JsonProperty("in_ORD_AMT")
    private long inOrdAmt;

    /** 지불_금액 (고객이 결제해야할 금액) */
    @JsonProperty("in_PAY_AMT")
    private long inPayAmt;

    /** 결제_구분 (1: 도착지결제, 2: 선결제) */
    @JsonProperty("in_CHARGE_TYPE")
    private String inChargeType;

    /** 지불_구분 (1: 현금, 2: 카드) */
    @JsonProperty("in_PAY_TYPE")
    private String inPayType;

    /** 상품_이름 (배달상품 간략 설명. 예: 통삼겹1인분 외 1건) */
    @JsonProperty("in_GOODS_NAMES")
    private String inGoodsNames;

    /** 만나주문_일련번호 */
    @JsonProperty("in_CANCEL_MEMO")
    private String inCancelMemo;
}
