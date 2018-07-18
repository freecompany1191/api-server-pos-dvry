package com.o2osys.pos.packet.v02.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.entity.rest.OrdInfo;

import lombok.Data;

/**
   @FileName  : OrdInfoPos.java
   @Description : 만나주문 목록 조회
   ServiceCode  : CB02_03
   @author      : 주정
   @since       : 2018. 5. 18.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2018. 5. 18.    주정		           최초생성

 */
@Data
public class inStOrdData {
	
	public inStOrdData() {
    }

    public inStOrdData(OrdInfo ordInfo) {
        this.ordNo = ordInfo.getOrdNo();
        this.posShopCode = ordInfo.getPosShopCode();
        this.posOrdCode = ordInfo.getPosOrdCode();
        this.posOrdDate = ordInfo.getPosOrdDate();
        this.ordPath = ordInfo.getOrdPath();
        this.ordStatus = ordInfo.getOrdStatus();
        this.ordCuTel = ordInfo.getOrdCuTel();
        this.modDate = ordInfo.getModDate();
        this.cthDate = ordInfo.getCthDate();
        this.cthWkTel = ordInfo.getCthWkTel();
        this.cthStName = ordInfo.getCthStName();
        this.pickupDate = ordInfo.getPickupDate();
        this.dvryAmt = "" + ordInfo.getDvryAmt();
        this.dvryExCharge = "" + ordInfo.getDvryExCharge();
        this.dvryMngFee = "" + ordInfo.getDvryMngFee();
    }
	
	/** 주문일련번호 */
    @JsonProperty("ORD_NO")
    private String ordNo;

    /** 고객사_가맹점_코드 */
    @JsonProperty("POS_SHOP_CODE")
    private String posShopCode;

    /** 고객사_주문_코드 (주문일련번호) */
    @JsonProperty("POS_ORD_CODE")
    private String posOrdCode;
    
    /** 고객사_주문_등록일 */
    @JsonProperty("POS_ORD_DATE")
    private String posOrdDate;
    
    /** 주문_경로 (1:배민앱 2:배달통앱 3:요기요앱 9:기타  ) */
    @JsonProperty("ORD_PATH")
    private String ordPath;

    /** 주문_상태 (1: 배달접수, 2: 배차, 3: 출발, 4: 완료, 5: 취소, 6: 대기) */
    @JsonProperty("ORD_STATUS")
    private String ordStatus;
    
    /** 고객 전화번호 */
    @JsonProperty("ORD_CU_TEL")
    private String ordCuTel;

    /** 최종수정_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("MOD_DATE")
    private String modDate;

    /** 배차_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("CTH_DATE")
    private String cthDate;
    
    /** 배차_배송원_전화번호 */
    @JsonProperty("CTH_WK_TEL")
    private String cthWkTel;
    
    /** 수주 가맹점 명 */
    @JsonProperty("CTH_ST_NAME")
    private String cthStName;

    /** 출발_일시 (YYYYMMDDHH24MISS) */
    @JsonProperty("PICKUP_DATE")
    private String pickupDate;

    /** 배달대행_금액 */
    @JsonProperty("DVRY_AMT")
    private String dvryAmt;

    /** 배달대행_할증_요금 */
    @JsonProperty("DVRY_EX_CHARGE")
    private String dvryExCharge;

    /** 배달대행_관리_가격 (건당 배송수수료) */
    @JsonProperty("DVRY_MNG_FEE")
    private String dvryMngFee;

}
