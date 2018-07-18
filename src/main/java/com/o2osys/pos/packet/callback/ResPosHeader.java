package com.o2osys.pos.packet.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
   @FileName  : ResPosMessage.java
   @Description : POS 업체용 연동 응답 헤더
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0
  
   @개정이력
   
   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성
 
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResPosHeader {
	
    public ResPosHeader() {
    }

    public ResPosHeader(String traceNo, String resCode, String resMsg, String serviceCode) {
        this.traceNo = traceNo;
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.serviceCode = serviceCode;
    }

    /** 처리결과코드 */
    @JsonProperty("TRACE_NO")
    private String traceNo;
    
    /** 처리결과코드 */
    @JsonProperty("RES_CODE")
    private String resCode;
    
    /** 처리결과메세지 */
    @JsonProperty("RES_MSG")
    private String resMsg;
    
    /** 서비스 전문 코드(오는값 그대로 리턴) */
    @JsonProperty("SERVICE_CODE")
    private String serviceCode;
}