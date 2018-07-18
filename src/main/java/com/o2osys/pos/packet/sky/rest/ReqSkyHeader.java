package com.o2osys.pos.packet.sky.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ReqSkyHeader.java
   @Description : 스카이포스 REST 요청 헤더
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.     KMS            최초생성

 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
public class ReqSkyHeader {

    public ReqSkyHeader() {}

    public ReqSkyHeader(String key, String traceNo, String serviceCode, String posTypeCode) {
        this.key = key;
        this.traceNo = traceNo;
        this.serviceCode = serviceCode;
        this.posTypeCode = posTypeCode;
    }

    /** 서버 인증키 */
    @JsonProperty("KEY")
    private String key;

    /** 전문추적번호 (YYYYMMDDHH24MISS + Random4자리) */
    @JsonProperty("TRACE_NO")
    private String traceNo;

    /** 서비스코드 */
    @JsonProperty("SERVICE_CODE")
    private String serviceCode;

    /** 업체구분코드 */
    @JsonProperty("POS_TYPE_CODE")
    private String posTypeCode;

}
