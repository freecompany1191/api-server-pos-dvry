package com.o2osys.pos.entity.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ShopInfo.java
   @Description : 만나가맹점 정보조회
   @ServiceCode : CB01_03
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성

 */
@Data
public class ShopInfo {

    public ShopInfo() {
    }

    public ShopInfo(ShopInfo shopInfo) {
        this.shopStatus = shopInfo.getShopStatus();
        this.shopCash = shopInfo.getShopCash();
    }

    /** 만나가맹점 정보조회 (CB01_03_V01) ********************************/
    /** 만나가맹점_상태 (0: 삭제, 1: 정상, 2: 정지, 9: 캐쉬부족) */
    @JsonProperty("SHOP_STATUS")
    private String shopStatus;

    /** 만나가맹점_캐쉬 */
    @JsonProperty("SHOP_CASH")
    private long shopCash;
}
