package com.o2osys.pos.packet.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResPosBody.java
   @Description :  POS 업체용 연동 응답 BODY 객체
   @author      : KMS
   @since       : 2017. 9. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 20.    KMS            최초생성
   2017.12. 26.    KMS            배달대행 금액 추가

 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResPosBody {

    public ResPosBody() {
    }

    public ResPosBody(String ordNo, String dvryAmt) {
        this.ordNo = ordNo;
        this.dvryAmt = dvryAmt;
    }

    /** 만나주문_일련번호 */
    @JsonProperty("ORD_NO")
    private String ordNo;

    /** 배달대행_금액 */
    @JsonProperty("DVRY_AMT")
    private String dvryAmt;

}
