package com.o2osys.pos.entity.rest;

import lombok.Data;

/**
   @FileName  : AddGoods.java
   @Description : 주문추가상품 목록
   @author      : KMS
   @since       : 2017. 10. 24.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 24.     KMS            최초생성

 */
@Data
public class AddGoods {

    public AddGoods() {
    }

    /** POS_상품_코드 */
    private String posAddGoodsCode;
    /** 추가_상품_일련번호 (수정을 위한 고유번호) */
    private long addGoodsNo;
    /** 프랜차이즈_상품_코드 */
    private String frGoodsCode;
    /** 추가_상품_이름 */
    private String addGoodsName;
    /** 추가_상품_개수 */
    private long addGoodsCnt;
    /** 추가_상품_가격 */
    private long addGoodsPrice;
    /** 주문_일련번호 */
    private long ordNo;
    /** 주문_상품_일련번호 (수정을 위한 고유번호) */
    private long ordGoodsNo;

}
