package com.o2osys.pos.packet.sky.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResSkyHeader.java
   @Description : 스카이포스 REST 수신 헤더
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.     KMS            최초생성

 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResSkyHeader {

    public ResSkyHeader() {
    }

    public ResSkyHeader(String traceNo, String resCode, String resMsg) {
        this.traceNo = traceNo;
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    /** 전문추적번호 (YYYYMMDDHH24MISS + Random4자리) */
    @JsonProperty("TRACE_NO")
    private String traceNo;

    /** 처리결과코드 */
    @JsonProperty("RES_CODE")
    private String resCode;

    /** 처리결과메세지 */
    @JsonProperty("RES_MSG")
    private String resMsg;
}
