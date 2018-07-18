package com.o2osys.pos.entity.rest;

import java.util.ArrayList;

import lombok.Data;

/**
   @FileName  : Goods.java
   @Description : 주문상품 목록
   @author      : KMS
   @since       : 2017. 10. 24.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 24.     KMS            최초생성

 */
@Data
public class Goods {

    public Goods() {
    }

    /** POS_상품_코드 */
    private String posGoodsCode;
    /** 주문_상품_일련번호 (수정을 위한 고유번호) */
    private long ordGoodsNo;
    /** 프랜차이즈_상품_코드 */
    private String frGoodsCode;
    /** 상품_이름 */
    private String GoodsName;
    /** 주문_개수 */
    private long ordCnt;
    /** 주문_가격 */
    private long ordPrice;
    /** 옵션_1_이름 */
    private String option1Name;
    /** 옵션_1_가격 */
    private long option1Price;
    /** 옵션_2_이름 */
    private String option2Name;
    /** 옵션_2_가격 */
    private long option2Price;
    /** 옵션_3_이름 */
    private String option3Name;
    /** 옵션_3_가격 */
    private long option3Price;
    /** 옵션_4_이름 */
    private String option4Name;
    /** 옵션_4_가격 */
    private long option4Price;
    /** 주문_일련번호 */
    private long ordNo;
    /** 주문추가상품 목록 */
    private ArrayList<AddGoods> addGoods;

}
