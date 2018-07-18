package com.o2osys.pos.common.location.packet.kakao.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
   @FileName  : ResCoord2address.java
   @Description : 좌표 -> 주소 변환
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
public class ResCoord2address {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private ArrayList<Documents> documents;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        /** 변환된 구(지번)주소/신주소 의 개수
         *  0 or 1
         **/
        @JsonProperty("total_count")
        private Integer totalCount;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Documents {

        /** 지번주소 상세 정보 */
        @JsonProperty("address")
        private Address address;

        /** 도로명주소 상세 정보 */
        @JsonProperty("road_address")
        private RoadAddress roadAddress;
    }

}
