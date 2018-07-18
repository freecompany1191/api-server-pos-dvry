package com.o2osys.pos.common.location.packet.kakao.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ReqAddress.java
   @Description : GET으로 요청 카카오 주소 검색 REST 파라메터
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
public class ReqAddress {

    /** [주소 검색] =========================================================*/
    /** 검색을 원하는 질의어(필수) */
    @NotNull
    @JsonProperty("query")
    private String query;

    /** 결과 페이지 번호
     *  X(기본 1)
     **/
    @JsonProperty("page")
    private Integer page;

    /** 한 페이지에 보여질 문서의 개수
     *  X(기본 10)
     **/
    @Min(1)
    @Max(30)
    @JsonProperty("size")
    private Integer size;

}
