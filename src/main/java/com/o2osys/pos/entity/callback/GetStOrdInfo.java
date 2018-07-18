package com.o2osys.pos.entity.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : OrdInfoPos.java
   @Description : 만나주문 목록 조회
   ServiceCode  : CB02_03
   @author      : KMS
   @since       : 2017. 11. 15.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 11. 15.   KMS            최초생성

 */
@Data
public class GetStOrdInfo {

    /** 고객사_가맹점_코드 */
    @JsonProperty("POS_SHOP_CODE")
    private String posShopCode;

    /** 고객사_주문_코드 (주문일련번호) */
    @JsonProperty("POS_ORD_CODE")
    private String posOrdCode;

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

    /** 배달대행_금액 */
    @JsonProperty("DVRY_AMT")
    private long dvryAmt;

    /** 배달대행_할증_요금 */
    @JsonProperty("DVRY_EX_CHARGE")
    private long dvryExCharge;

    /** 배달대행_관리_가격 (건당 배송수수료) */
    @JsonProperty("DVRY_MNG_FEE")
    private long dvryMngFee;

}
