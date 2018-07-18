package com.o2osys.pos.common.location.packet.kakao.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : Address.java
   @Description : 지번주소 상세 정보
   @author      : KMS
   @since       : 2017. 8. 10.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 8. 10.     KMS            최초생성

 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    /** 전체 지번 주소 */
    @JsonProperty("address_name")
    private String addressName;

    /** 지역 1Depth명 - 시도 단위 */
    @JsonProperty("region_1depth_name")
    private String region1depthName;

    /** 지역 2Depth명 - 구 단위 */
    @JsonProperty("region_2depth_name")
    private String region2depthName;

    /** 지역 3Depth명 - 동 단위 */
    @JsonProperty("region_3depth_name")
    private String region3depthName;

    /** 지역 3Depth 행정동 명칭 */
    @JsonProperty("region_3depth_h_name")
    private String region3depthHName;

    /** 행정 코드 */
    @JsonProperty("h_code")
    private String hCode;

    /** 법정 코드 */
    @JsonProperty("b_code")
    private String bCode;

    /** 산 여부 */
    @JsonProperty("mountain_yn")
    private String mountainYn;

    /** 지번 주 번지 */
    @JsonProperty("main_address_no")
    private String mainAddressNo;

    /** 지번 부 번지. 없을 경우 "" */
    @JsonProperty("sub_address_no")
    private String subAddressNo;

    /** 우편번호 (6자리) */
    @JsonProperty("zip_code")
    private String zipCode;

    /** X 좌표값 혹은 longitude */
    @JsonProperty("x")
    private String longitude;

    /** Y 좌표값 혹은 latitude */
    @JsonProperty("y")
    private String latitude;

}
