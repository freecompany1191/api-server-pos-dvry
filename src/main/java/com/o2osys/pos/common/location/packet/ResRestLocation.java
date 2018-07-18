package com.o2osys.pos.common.location.packet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.packet.ResCommon;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResRestLocation extends ResCommon {

    @JsonProperty("header")
    private ResResLocationHeader header;

    @JsonProperty("body")
    private ResResLocationBody body;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResResLocationHeader {

        /** 전문추적번호 - YYYYMMDDHH24MISS + Random3자리 */
        @JsonProperty("TRACE_NO")
        private String traceNo;

        /** 응답코드 실패: 0, 성공: 1 */
        @JsonProperty("RES_CODE")
        private String resCode;

        /** 응답메세지 */
        @JsonProperty("RES_MSG")
        private String resMsg;

        /** 접속성공여부 */
        private boolean conn;
    }

    @Data
    public static class ResResLocationBody extends ResCommon {

        /** 응답주소 */
        @JsonProperty("LOCATION")
        private Location location;

    }
}
