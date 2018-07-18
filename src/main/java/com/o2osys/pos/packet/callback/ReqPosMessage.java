package com.o2osys.pos.packet.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ReqMessage.java
   @Description : POS 업체용 연동 요청 객체
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성

 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqPosMessage {

    @JsonProperty("header")
    private ReqPosMsgHeader header;

    @JsonProperty("body")
    private ReqPosMsgBody body;

    @Data
    public class ReqPosMsgHeader {

        /** 발행받은 인증키 값 */
        @JsonProperty("KEY")
        private String key;

        /** 송수신 일련번호 (요청측에서 보내는 일련번호. YYYYMMDDHH24MISS + RANDUM NUMBER(4)) */
        @JsonProperty("TRACE_NO")
        private String traceNo;

        /** 요청전문번호 */
        @JsonProperty("SERVICE_CODE")
        private String serviceCode;

        /** 업체구분코드 */
        @JsonProperty("POS_TYPE_CODE")
        private String posTypeCode;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ReqPosMsgBody {

        public ReqPosMsgBody() {
        }
        
        /** 만나 주문코드 */
        @JsonProperty("in_ORD_NO")
        private String inOrdNo;
        
        /** 마지막싱크데이트 */
        @JsonProperty("in_LAST_SYNC_DATE")
        private String inLastSyncDate;

        /** 만나가맹점 검색 (CB01_01_V01) ************************************/
        /** 검색_구분 (1: 가맹점명, 2: 사업자번호, 3: 전화번호, 4: 사업주명) */
        @JsonProperty("in_FIND_TYPE")
        private String inFindType;

        /** 검색_단어 (검색구분에 따른 검색단어) */
        @JsonProperty("in_FIND_VAL")
        private String inFindVal;

        /** 고객사 가맹점 정보 등록/삭제 (CB01_02) ***********************/
        /** 만나가맹점 정보조회 (CB01_03) ********************************/

        /** 만나가맹점_코드 */
        @JsonProperty("in_SHOP_CODE")
        private String inShopCode;

        /** 배달요청 주문 전송(CB02_01) **********************************/
        /** 주문 정보 출력(CB02_02) **********************************/
        /** 고객사_가맹점_코드 */
        @JsonProperty("in_POS_SHOP_CODE")
        private String inPosShopCode;

        /** 처리_구분[CB01_02] (1: 등록, 2: 삭제) */
        /** 처리_구분[CB02_01] (1: 배달요청, 2: 주문수정) */
        @JsonProperty("in_ACT_TYPE")
        private String inActType;

        /** 주문 정보 출력(CB02_02) **********************************/
        /** 고객사_주문_코드 (주문일련번호) */
        @JsonProperty("in_POS_ORD_CODE")
        private String inPosOrdCode;

        /** 주문 정보 출력(CB02_02) **********************************/
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

        /** 도착_주소_1 (시/도) */
        @JsonProperty("in_EA_ADDR_1")
        private String inEaAddr1;

        /** 도착_주소_2 (시/군/구) */
        @JsonProperty("in_EA_ADDR_2")
        private String inEaAddr2;

        /** 도착_주소_3 (읍/면/동) */
        @JsonProperty("in_EA_ADDR_3")
        private String inEaAddr3;

        /** 도착_주소_지번주소 (구주소, 전체 주소) */
        @JsonProperty("in_EA_ADDR_JB")
        private String inEaAddrJb;

        /** 도착_주소_도로명주소 (신주소, 전체 주소) */
        @JsonProperty("in_EA_ADDR_ST")
        private String inEaAddrSt;

        /** 도착_주소_상세주소 (사용자입력 상세주소) */
        @JsonProperty("in_EA_ADDR_ETC")
        private String inEaAddrEtc;

        /** 도착_위도_좌표 (Y좌표, LATITUDE) */
        @JsonProperty("in_EA_LAT_Y")
        private String inEaLatY;

        /** 도착_경도_좌표 (X좌표, LONGITUDE) */
        @JsonProperty("in_EA_LNG_X")
        private String inEaLngX;

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

}
