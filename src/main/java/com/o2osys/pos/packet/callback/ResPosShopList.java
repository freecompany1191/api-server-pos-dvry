package com.o2osys.pos.packet.callback;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.entity.callback.ShopList;
import com.o2osys.pos.packet.ResCommon;

import lombok.Data;


/**
   @FileName  : ResPosMessage.java
   @Description : POS 업체용 연동 만나가맹점 검색 (CB01_01_V01) 응답 객체
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성

 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResPosShopList extends ResCommon {

    public ResPosShopList() {
    }

    public ResPosShopList(ArrayList<ShopList> shopList) {
        this.shopList = shopList;
    }

    /** 만나가맹점 검색 (CB01_01) ************************************/
    /** 검색가맹점_목록 */
    @JsonProperty("SHOP_LIST")
    private ArrayList<ShopList> shopList;

}