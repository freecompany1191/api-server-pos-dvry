package com.o2osys.pos.common.location.packet;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.common.location.packet.kakao.response.Address;
import com.o2osys.pos.common.location.packet.kakao.response.ResAddress.Documents;
import com.o2osys.pos.common.location.packet.kakao.response.RoadAddress;

import lombok.Data;

/**
   @FileName  : Location.java
   @Description : MCS 주소정보 객체
   @author      : KMS
   @since       : 2017. 8. 30.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 8. 30.     KMS            최초생성

 */
@Data
public class Location {

    public Location() {
    }

    public Location(Documents doc) {
        this.addressName = doc.getAddressName();
        this.addressType = doc.getAddressType();
        this.longitude = doc.getLongitude();
        this.latitude = doc.getLatitude();
        this.address = doc.getAddress();
        this.roadAddress = doc.getRoadAddress();

        /** 도착_경도_좌표 (X좌표, LONGITUDE) */
        this.eaLngX = this.longitude;
        /** 도착_위도_좌표 (Y좌표, LATITUDE) */
        this.eaLatY = this.latitude;

        /** 도로명이 있으면 도로명 우선 */
        if(this.roadAddress !=null){
            /** 도착_주소_1 (지도API, 시/도) */
            this.eaAddr1 = this.roadAddress.getRegion1depthName();
            /** 도착_주소_2 (지도API, 시/군/구) */
            this.eaAddr2 = this.roadAddress.getRegion2depthName();
            /** 도착_주소_3 (읍/면/동/리) */
            this.eaAddr3 = this.roadAddress.getRegion3depthName();
            /** 도착_주소_6 (도로명 전체주소) */
            this.eaAddr6 = this.roadAddress.getAddressName();
            /** 도착_주소_9 (도로명) */
            this.eaAddr9 = this.roadAddress.getRoadName();
            /** 도착_주소_10 (건물명) */
            this.eaAddr10 = this.roadAddress.getBuildingName();
            /** 도착_주소_12 (건물번호 (본번-부번)) */
            this.eaAddr12 = this.roadAddress.getMainBuildingNo();
            /** 부번이 있을경우 주번 뒤에 붙임 */
            if(!StringUtils.isEmpty(this.roadAddress.getSubBuildingNo()))
                this.eaAddr12 = this.eaAddr12+"-"+this.roadAddress.getSubBuildingNo();
        }
        /** 도로명이 없으면 지번 */
        else if(this.address != null){
            /** 도착_주소_1 (지도API, 시/도) */
            this.eaAddr1 = this.address.getRegion1depthName();
            /** 도착_주소_2 (지도API, 시/군/구) */
            this.eaAddr2 = this.address.getRegion2depthName();
            /** 도착_주소_3 (읍/면/동/리) */
            this.eaAddr3 = this.address.getRegion3depthName();
        }
        /** 지번 주소가 있으면 나머지 정보 채움 */
        if(this.address != null){
            /** 도착_주소_4 (지번 전체주소) */
            this.eaAddr4 = this.address.getAddressName();
            /** 지역 3Depth 행정동 명칭 */
            this.eaAddr8 = this.address.getRegion3depthHName();
            /** 도착_주소_11 (지번 (주번-부번)) */
            this.eaAddr11 = this.address.getMainAddressNo();
            /** 부번이 있을경우 주번 뒤에 붙임 */
            if(!StringUtils.isEmpty(this.address.getSubAddressNo()))
                this.eaAddr11 = this.eaAddr11+"-"+this.address.getSubAddressNo();
        }
    }

    /** 전체 지번 주소 또는 전체 도로명 주소. (input에 따라 결정됨) */
    @JsonProperty("address_name")
    private String addressName;

    /** address_name의 값의 type. 지명, 도로명, 지번주소, 도로명주소 여부
     *  REGION or ROAD or REGION_ADDR or ROAD_ADDR
     **/
    @JsonProperty("address_type")
    private String addressType;

    /** X 좌표값 혹은 longitude */
    @JsonProperty("x")
    private String longitude;

    /** Y 좌표값 혹은 latitude */
    @JsonProperty("y")
    private String latitude;

    /** 지번주소 상세 정보 */
    @JsonProperty("address")
    private Address address;

    /** 도로명주소 상세 정보 */
    @JsonProperty("road_address")
    private RoadAddress roadAddress;

    /** DB등록용 변수 */
    /** 좌표_정확도_구분 (1: 높음, 2: 중간, 3: 낮음) */
    @JsonProperty("xy_acc_type")
    private String xyAccType;

    /** 도착_경도_좌표 (X좌표, LONGITUDE) */
    @JsonProperty("ea_lng_x")
    private String eaLngX;
    /** 도착_위도_좌표 (Y좌표, LATITUDE) */
    @JsonProperty("ea_lat_y")
    private String eaLatY;

    /** 도착_주소_1 (지도API, 시/도) */
    @JsonProperty("ea_addr_1")
    private String eaAddr1;
    /** 도착_주소_2 (지도API, 시/군/구) */
    @JsonProperty("ea_addr_2")
    private String eaAddr2;
    /** 도착_주소_3 (읍/면/동/리) */
    @JsonProperty("ea_addr_3")
    private String eaAddr3;

    /** 도착_주소_4 (지번 전체주소) */
    @JsonProperty("ea_addr_4")
    private String eaAddr4;
    /** 도착_주소_5 (사용자 입력주소) */
    @JsonProperty("ea_addr_5")
    private String eaAddr5;
    /** 도착_주소_6 (도로명 전체주소) */
    @JsonProperty("ea_addr_6")
    private String eaAddr6;
    /** 도착_주소_7 (외부연동 전체주소 (배민, 요기요 등)) */
    @JsonProperty("ea_addr_7")
    private String eaAddr7;

    /** 도착_주소_8 (행정동) */
    @JsonProperty("ea_addr_8")
    private String eaAddr8;
    /** 도착_주소_9 (도로명) */
    @JsonProperty("ea_addr_9")
    private String eaAddr9;
    /** 도착_주소_10 (건물명) */
    @JsonProperty("ea_addr_10")
    private String eaAddr10;
    /** 도착_주소_11 (지번 (주번-부번)) */
    @JsonProperty("ea_addr_11")
    private String eaAddr11;
    /** 도착_주소_12 (건물번호 (본번-부번)) */
    @JsonProperty("ea_addr_12")
    private String eaAddr12;

}
