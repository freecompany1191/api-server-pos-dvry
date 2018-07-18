package com.o2osys.pos.common.location.packet.kakao.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : RoadAddress.java
   @Description : 도로명주소 상세 정보
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
public class RoadAddress {

    /** 전체 도로명 주소 */
    @JsonProperty("address_name")
    private String addressName;

    /** 지역 1Depth명 - 시도 단위 */
    @JsonProperty("region_1depth_name")
    private String region1depthName;

    /** 지역 2Depth명 - 구 단위 */
    @JsonProperty("region_2depth_name")
    private String region2depthName;

    /** 지역 3Depth명 - 면 단위 */
    @JsonProperty("region_3depth_name")
    private String region3depthName;

    /** 도로명 */
    @JsonProperty("road_name")
    private String roadName;

    /** 지하 여부
     *	Y or N
     **/
    @JsonProperty("underground_yn")
    private String undergroundYn;

    /** 건물 본번 */
    @JsonProperty("main_building_no")
    private String mainBuildingNo;

    /** 건물 부번. 없을 경우 "" */
    @JsonProperty("sub_building_no")
    private String subBuildingNo;

    /** 건물 이름 */
    @JsonProperty("building_name")
    private String buildingName;

    /** 우편번호(5자리) */
    @JsonProperty("zone_no")
    private String zoneNo;

    /** X 좌표값 혹은 longitude */
    @JsonProperty("x")
    private String longitude;

    /** Y 좌표값 혹은 latitude */
    @JsonProperty("y")
    private String latitude;

}
