package com.o2osys.pos.entity.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ShopList.java
   @Description : 검색가맹점 목록
   @ServiceCode : CB01_01
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성

 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopList {

    /** 만나가맹점_코드 */
    @JsonProperty("SHOP_CODE")
    private String shopCode;

    /** 만나가맹점_이름 */
    @JsonProperty("SHOP_NAME")
    private String shopName;

    /** 만나가맹점_전화번호 */
    @JsonProperty("SHOP_TEL")
    private String shopTel;

    /** 만나가맹점_대표자명 */
    @JsonProperty("SHOP_OWNER")
    private String shopOwner;

    /** 사업자_번호 (계산서발행용) */
    @JsonProperty("BIZ_NUM")
    private String bizNum;

    /** 만나가맹점_주소 */
    @JsonProperty("SHOP_ADDR")
    private String shopAddr;

}
