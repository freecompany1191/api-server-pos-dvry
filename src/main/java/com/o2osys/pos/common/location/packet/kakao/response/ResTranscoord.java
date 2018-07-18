package com.o2osys.pos.common.location.packet.kakao.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResTranscoord.java
   @Description : 좌표계 변환
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
public class ResTranscoord {

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

        /** X 좌표값 혹은 longitude */
        @JsonProperty("x")
        private String longitude;

        /** Y 좌표값 혹은 latitude */
        @JsonProperty("y")
        private String latitude;
    }

}
