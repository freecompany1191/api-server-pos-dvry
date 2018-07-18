package com.o2osys.pos.packet.sky.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.entity.rest.AddGoods;

import lombok.Data;

/**
   @FileName  : InAddGoodsData.java
   @Description : 스카이포스 추가_상품_목록
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.     KMS            최초생성

 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
public class InAddGoodsData {

    public InAddGoodsData() {
    }

    public InAddGoodsData(AddGoods addGoods) {
        this.inPosAddGoodsCode = addGoods.getPosAddGoodsCode(); //POS_상품_코드
        this.inAddGoodsNo = "" + addGoods.getAddGoodsNo();        //추가_상품_일련번호 (수정을 위한 고유번호)
        //this.inFrGoodsCode = addGoods.getFrGoodsCode();      //프랜차이즈_상품_코드
        this.inAddGoodsName = addGoods.getAddGoodsName();    //추가_상품_이름
        this.inAddGoodsCnt = "" + addGoods.getAddGoodsCnt();      //추가_상품_개수
        this.inAddGoodsPrice = "" + addGoods.getAddGoodsPrice();  //추가_상품_가격
    }

    /** 고객사_추가_상품_코드 */
    @JsonProperty("in_POS_ADD_GOODS_CODE")
    private String inPosAddGoodsCode;

    /** 만나주문_추가_상품_일련번호 */
    @JsonProperty("in_ADD_GOODS_NO")
    private String inAddGoodsNo;

    /** 프랜차이즈_상품_코드 */
    //@JsonProperty("in_FR_GOODS_CODE")
    //private String inFrGoodsCode;

    /** 추가_상품_이름 */
    @JsonProperty("in_ADD_GOODS_NAME")
    private String inAddGoodsName;

    /** 추가_상품_개수 */
    @JsonProperty("in_ADD_GOODS_CNT")
    private String inAddGoodsCnt;

    /** 추가_상품_가격 */
    @JsonProperty("in_ADD_GOODS_PRICE")
    private String inAddGoodsPrice;
}
