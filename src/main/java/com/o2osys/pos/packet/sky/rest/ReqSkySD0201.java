package com.o2osys.pos.packet.sky.rest;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.entity.rest.ConInfo;
import com.o2osys.pos.entity.rest.Goods;

import lombok.Data;

/**
   @FileName  : ReqSkySD0201.java
   @ReqNum      : SD02_01_V01
   @Description : 만나주문 전송
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.     KMS            최초생성

 */
@Data
@JsonInclude(Include.ALWAYS)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReqSkySD0201 {

    public ReqSkySD0201() {
    }

    public ReqSkySD0201(ReqSkyHeader header, ReqSkySD0201Body body) {
        this.header = header;
        this.body = body;
    }

    @JsonProperty("header")
    private ReqSkyHeader header;

    @JsonProperty("body")
    private ReqSkySD0201Body body;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(Include.ALWAYS)
    //@JsonInclude(value=Include.NON_ABSENT, content=Include.NON_EMPTY)
    public static class ReqSkySD0201Body {

        public ReqSkySD0201Body() {
        }

        public ReqSkySD0201Body(ConInfo con) {
            this.inPosShopCode = con.getPosShopCode(); //POS_가맹점_코드
            this.inOrdNo = con.getOrdNo();             //주문_일련번호
            this.inOrdCuTel = con.getOrdCuTel();       //주문_고객_전화번호
            this.inOrdCuTel2 = con.getOrdCuTel2();     //주문_고객_전화번호_2 (연락번호)
            this.inOrdMemo = con.getOrdMemo();         //주문_메모
            this.inTakeoutYn = con.getTakeoutYn();     //테이크아웃_여부
            this.inTakeoutDate = con.getTakeoutDate(); //테이크아웃_일시 (YYYYMMDDHH24MISS. 테이크아웃_여부가 'Y'일 경우 전송)
            this.inReadyTime = con.getReadyTime();     //준비_시간 (단위: 분, 조리시간)
            this.inEaAddr1 = con.getEaAddr1();         //도착_주소_1 (시/도)
            this.inEaAddr2 = con.getEaAddr2();         //도착_주소_2 (시/군/구)
            this.inEaAddr3 = con.getEaAddr3();         //도착_주소_3 (읍/면/동/리)
            this.inEaAddrJb = con.getEaAddrJb();       //도착_주소_지번주소 (구주소, 전체 주소. 예: 서울특별시 구로구 신도림동 337)
            this.inEaAddrSt = con.getEaAddrSt();       //도착_주소_도로명주소 (신주소, 전체 주소. 예: 서울특별시 구로구 경인로 661)
            this.inEaAddrEtc = con.getEaAddrEtc();     //도착_주소_상세주소 (사용자입력 상세주소. 예: 푸르지오1차 104동 906호)
            this.inEaLatY = con.getEaLatY();           //도착_위도_좌표 (Y좌표, LATITUDE)
            this.inEaLngX = con.getEaLngX();           //도착_경도_좌표 (X좌표, LONGITUDE)
            this.inOrdAmt = con.getOrdAmt();           //주문_금액 (총액)
            this.inPayAmt = con.getPayAmt();           //지불_금액 (총액)
            this.inChargeType = con.getChargeType();   //결제_구분 (1: 도착지결제, 2: 선결제)
            this.inPayType = con.getPayType();         //지불_구분 (1: 현금, 2: 카드)
            this.inFrTypeCode = con.getFrTypeCode();   //프랜차이즈_구분_코드
            this.inGoodsNames = con.getGoodsNames();   //상품_이름 (표시 이름)

            this.inGoodsData = new ArrayList<InGoodsData>(); //상품_목록

            //루프문 돌면서 주문상품 추가
            for(Goods good : con.getGoods()){
                this.inGoodsData.add(new InGoodsData(good));
            }

        }

        /** 고객사_가맹점_코드 */
        @JsonProperty("in_POS_SHOP_CODE")
        private String inPosShopCode;

        /** 만나주문_일련번호 */
        @JsonProperty("in_ORD_NO")
        private String inOrdNo;

        /** 주문_고객_전화번호 */
        @JsonProperty("in_ORD_CU_TEL")
        private String inOrdCuTel;

        /** 주문_고객_전화번호_2 (수령고객. 주문고객과 수령고객이 같을 경우 주문_고객_전화번호로 전달) */
        @JsonProperty("in_ORD_CU_TEL_2")
        private String inOrdCuTel2;

        /** 주문_메모 */
        @JsonProperty("in_ORD_MEMO")
        private String inOrdMemo;

        /** 테이크아웃_여부 */
        @JsonProperty("in_TAKEOUT_YN")
        private String inTakeoutYn;

        /** 테이크아웃_일시 (YYYYMMDDHH24MISS. 테이크아웃_여부가 'Y'일 경우 전송) */
        @JsonProperty("in_TAKEOUT_DATE")
        private String inTakeoutDate;

        /** 준비_시간 (단위: 분, 조리시간) */
        @JsonProperty("in_READY_TIME")
        private String inReadyTime;

        /** 도착_주소_1 (시/도) */
        @JsonProperty("in_EA_ADDR_1")
        private String inEaAddr1;

        /** 도착_주소_2 (시/군/구) */
        @JsonProperty("in_EA_ADDR_2")
        private String inEaAddr2;

        /** 도착_주소_3 (읍/면/동) */
        @JsonProperty("in_EA_ADDR_3")
        private String inEaAddr3;

        /** 도착_주소_지번주소 (구주소, 전체 주소. 예: 서울특별시 구로구 신도림동 337) */
        @JsonProperty("in_EA_ADDR_JB")
        private String inEaAddrJb;

        /** 도착_주소_도로명주소 (신주소, 전체 주소. 예: 서울특별시 구로구 경인로 661) */
        @JsonProperty("in_EA_ADDR_ST")
        private String inEaAddrSt;

        /** 도착_주소_상세주소 (사용자입력 상세주소. 예: 푸르지오1차 104동 906호) */
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
        private String inOrdAmt;

        /** 지불_금액 (고객이 결제해야할 금액) */
        @JsonProperty("in_PAY_AMT")
        private String inPayAmt;

        /** 결제_구분 (1: 도착지결제, 2: 선결제)) */
        @JsonProperty("in_CHARGE_TYPE")
        private String inChargeType;

        /** 지불_구분 (1: 현금, 2: 카드) */
        @JsonProperty("in_PAY_TYPE")
        private String inPayType;

        /** 프랜차이즈_구분_코드 */
        @JsonProperty("in_FR_TYPE_CODE")
        private String inFrTypeCode;

        /** 상품_요약이름 (배달상품 간략 설명. 예: 통삼겹1인분 외 1건) */
        @JsonProperty("in_GOODS_NAMES")
        private String inGoodsNames;

        /** 상품_목록 */
        @JsonProperty("in_GOODS_DATA")
        private ArrayList<InGoodsData> inGoodsData;

    }

}
