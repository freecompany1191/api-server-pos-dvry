package com.o2osys.pos.packet.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
   @FileName  : ResPosMessage.java
   @Description : POS 업체용 연동 응답 객체
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
public class ResPosMessage {
	
	public ResPosMessage() {
    }
	
	public ResPosMessage(ResPosMessage res) {
        this.header = res.getHeader();
        this.body = res.getBody();
    }
	
	public ResPosMessage(ResPosHeader header) {
        this.header = header;
    }
	
    
    public ResPosMessage(ResPosHeader header, Object body) {
        this.header = header;
        this.body = body;
    }

    @JsonProperty("header")    
    private ResPosHeader header;
    
    @JsonProperty("body")
    private Object body;
    
}