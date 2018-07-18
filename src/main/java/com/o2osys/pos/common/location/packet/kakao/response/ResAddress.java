package com.o2osys.pos.common.location.packet.kakao.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResAddress.java
   @Description : 주소 검색
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
public class ResAddress {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private ArrayList<Documents> documents;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        /** 검색어에 검색된 문서수 */
        @JsonProperty("total_count")
        private Integer totalCount;

        /** total_count 중에 노출가능 문서수 */
        @JsonProperty("pageable_count")
        private Integer pageableCount;

        /** 현재 페이지가 마지막 페이지인지 여부. 값이 false이면 page를 증가시켜 다음 페이지를 요청할 수 있음. */
        @JsonProperty("is_end")
        private boolean isEnd;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Documents {

        /** 전체 지번 주소 또는 전체 도로명 주소. (input에 따라 결정됨) */
        @JsonProperty("address_name")
        private String addressName;

        /** X 좌표값 혹은 longitude */
        @JsonProperty("x")
        private String longitude;

        /** Y 좌표값 혹은 latitude */
        @JsonProperty("y")
        private String latitude;

        /** address_name의 값의 type. 지명, 도로명, 지번주소, 도로명주소 여부
         *  REGION or ROAD or REGION_ADDR or ROAD_ADDR
         **/
        @JsonProperty("address_type")
        private String addressType;

        /** 지번주소 상세 정보 */
        @JsonProperty("address")
        private Address address;

        /** 도로명주소 상세 정보 */
        @JsonProperty("road_address")
        private RoadAddress roadAddress;

    }

}
