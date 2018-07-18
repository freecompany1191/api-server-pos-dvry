package com.o2osys.pos.entity.callback;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class GetStOrdInfo_V02 {
	
	/** 마지막 동기화 날짜 */
    @JsonProperty("LAST_SYNC_DATE")
    private String lastSyncDate;
    
    /** 주문 목록 */
    @JsonProperty("POS_ORD_DATA")
    private ArrayList<com.o2osys.pos.packet.v02.rest.inStOrdData> stOrdList;
}
