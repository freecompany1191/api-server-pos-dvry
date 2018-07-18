package com.o2osys.pos.common.location.packet.kakao.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResCoord2regioncodejava.java
   @Description : 좌표 → 행정구역정보 변환
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
public class ResCoord2regioncodejava {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private ArrayList<Documents> documents;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        /** 매칭된 문서수 */
        @JsonProperty("total_count")
        private Integer totalCount;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Documents {

        /** 지상/지하/공중 여부
         *  H(행정동) or B(법정동)
         **/
        @JsonProperty("region_type")
        private String regionType;

        /** 전체 지역 명칭 */
        @JsonProperty("address_name")
        private String addressName;

        /** 지역 1Depth명 - 시도 단위(바다지역시 존재안함) */
        @JsonProperty("region_1depth_name")
        private String region1depthName;

        /** 지역 2Depth명 - 구 단위(바다지역시 존재안함) */
        @JsonProperty("region_2depth_name")
        private String region2depthName;

        /** 지역 3Depth명 - 동 단위(바다지역시 존재안함) */
        @JsonProperty("region_3depth_name")
        private String region3depthName;

        /** 지역 4Depth명 - region_type 이 법정동이며, 리 영역인 경우만 존재 */
        @JsonProperty("region_4depth_name")
        private String region4depthName;

        /** region 코드 */
        @JsonProperty("code")
        private String code;

        /** X 좌표값 혹은 longitude */
        @JsonProperty("x")
        private String longitude;

        /** Y 좌표값 혹은 latitude */
        @JsonProperty("y")
        private String latitude;
    }

}
