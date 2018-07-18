package com.o2osys.pos.packet.sky.rest;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.entity.rest.AddGoods;
import com.o2osys.pos.entity.rest.Goods;

import lombok.Data;

/**
   @FileName  : InGoodsData.java
   @Description : 스카이포스 상품_목록
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
public class InGoodsData {

    public InGoodsData() {
    }

    public InGoodsData(Goods goods) {
        this.inPosGoodsCode = goods.getPosGoodsCode(); //POS_상품_코드
        this.inOrdGoodsNo = "" + goods.getOrdGoodsNo();     //주문_상품_일련번호 (수정을 위한 고유번호)
        //this.inFrGoodsCode = goods.getFrGoodsCode();   //프랜차이즈_상품_코드
        this.inGoodsName = goods.getGoodsName();       //상품_이름
        this.inOrdCnt = "" + goods.getOrdCnt();             //주문_개수
        this.inOrdPrice = "" + goods.getOrdPrice();         //주문_가격
        this.inOption1Name = "" + goods.getOption1Name();   //옵션_1_이름
        this.inOption1Price = "" + goods.getOption1Price(); //옵션_1_가격
        this.inOption2Name = goods.getOption2Name();   //옵션_2_이름
        this.inOption2Price = "" + goods.getOption2Price(); //옵션_2_가격

        this.inAddGoodsData = new ArrayList<InAddGoodsData>(); //주문추가상품 목록

        //루프문 돌면서 주문추가상품 추가
        for(AddGoods addGood : goods.getAddGoods()){
            this.inAddGoodsData.add(new InAddGoodsData(addGood));
        }

    }

    /** 고객사_상품_코드 */
    @JsonProperty("in_POS_GOODS_CODE")
    private String inPosGoodsCode;

    /** 만나주문_상품_일련번호 */
    @JsonProperty("in_ORD_GOODS_NO")
    private String inOrdGoodsNo;

    /** 프랜차이즈_상품_코드 */
    //@JsonProperty("in_FR_GOODS_CODE")
    //private String inFrGoodsCode;

    /** 상품_이름 */
    @JsonProperty("in_GOODS_NAME")
    private String inGoodsName;

    /** 주문_개수 */
    @JsonProperty("in_ORD_CNT")
    private String inOrdCnt;

    /** 주문_가격 (상품단가) */
    @JsonProperty("in_ORD_PRICE")
    private String inOrdPrice;

    /** 옵션_1_이름 */
    @JsonProperty("in_OPTION_1_NAME")
    private String inOption1Name;

    /** 옵션_1_가격 */
    @JsonProperty("in_OPTION_1_PRICE")
    private String inOption1Price;

    /** 옵션_2_이름 */
    @JsonProperty("in_OPTION_2_NAME")
    private String inOption2Name;

    /** 옵션_2_가격 */
    @JsonProperty("in_OPTION_2_PRICE")
    private String inOption2Price;

    /** 추가_상품_목록 */
    @JsonProperty("in_ADD_GOODS_DATA")
    private ArrayList<InAddGoodsData> inAddGoodsData;
}
