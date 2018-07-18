package com.o2osys.pos.packet.sky.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResInputOrder.java
   @Description : 스카이포스 REST 배송요청(API_DVRY_INPUT_ORDER) 응답 객체
   @author      : KMS
   @since       : 2017. 9. 7.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 7.     KMS            최초생성

 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class ResSkyCommon {

    public ResSkyCommon() {
    }

    public ResSkyCommon(ResSkyHeader header, ResSkyCommonBody body) {
        this.header = header;
        this.body = body;
    }

    @JsonProperty("header")
    private ResSkyHeader header;

    @JsonProperty("body")
    private ResSkyCommonBody body;

    @Data
    @JsonInclude(Include.NON_EMPTY)
    public static class ResSkyCommonBody {

        public ResSkyCommonBody() {
        }

        public ResSkyCommonBody(String ordNo, String posShopCode, String posOrdCode, String posOrdDate) {
            this.ordNo = ordNo;
            this.posShopCode = posShopCode;
            this.posOrdCode = posOrdCode;
            this.posOrdDate = posOrdDate;
        }

        /** 만나주문_일련번호 */
        @JsonProperty("ORD_NO")
        private String ordNo;

        /** 고객사_가맹점_코드 */
        @JsonProperty("POS_SHOP_CODE")
        private String posShopCode;

        /** 고객사_주문_코드 (주문일련번호) */
        @JsonProperty("POS_ORD_CODE")
        private String posOrdCode;

        /** 고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우) */
        @JsonProperty("POS_ORD_DATE")
        private String posOrdDate;

    }

}