package com.o2osys.pos.common.location.packet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.o2osys.pos.packet.ResCommon;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRestLocation extends ResCommon {

    public ReqRestLocation() {
    }

    public ReqRestLocation(ReqHeader header, ReqBody body) {
        this.header = header;
        this.body = body;
    }

    @JsonProperty("header")
    private ReqHeader header;

    @JsonProperty("body")
    private ReqBody body;

    @Data
    public static class ReqHeader {

        public ReqHeader() {
        }

        public ReqHeader(String traceNo, String serviceCode) {
            this.traceNo = traceNo;
            this.serviceCode = serviceCode;
        }

        /** 전문추적번호 - YYYYMMDDHH24MISS + Random3자리 */
        @JsonProperty("TRACE_NO")
        private String traceNo;

        /** 각 기능별 Service Code */
        @JsonProperty("SERVICE_CODE")
        private String serviceCode;

    }

    @Data
    public static class ReqBody {

        public ReqBody() {
        }

        public ReqBody(String address) {
            this.address = address;
        }

        /** 요청주소 */
        @JsonProperty("ADDRESS")
        private String address;

    }
}
