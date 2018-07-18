package com.o2osys.pos.common.location.packet.kakao.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ReqKeyword.java
   @Description : GET으로 요청 카카오 키워드로 장소 검색 REST 파라메터
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
public class ReqKeyword {

    /** [주소 검색] =========================================================*/
    /** 검색을 원하는 질의어(필수) */
    @NotNull
    @JsonProperty("query")
    private String query;

    /** 카테고리 그룹 코드. 결과를 카테고리로 필터링을 원하는 경우 사용. */
    @JsonProperty("category_group_code")
    private String categoryGroupCode;

    /** 중심 좌표의 X값 혹은 longitude. 특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능. */
    @JsonProperty("x")
    private String longitude;

    /** 중심 좌표의 Y값 혹은 latitude. 특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능. */
    @JsonProperty("y")
    private String latitude;

    /** 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용. 단위 meter
     *  X(기본 5000)
     **/
    @Min(0)
    @Max(20000)
    @JsonProperty("radius")
    private Integer radius;

    /** 사각형 범위내에서 제한 검색을 위한 좌표. 지도 화면 내 검색시 등 제한 검색에서 사용가능. 좌측X좌표,좌측Y좌표,우측X좌표,우측Y좌표 형식. */
    @JsonProperty("rect")
    private String rect;

    /** 결과 페이지 번호
     *  X(기본 1)
     **/
    @JsonProperty("page")
    private Integer page;

    /** 한 페이지에 보여질 문서의 개수
     *  X(기본 15)
     **/
    @Min(1)
    @Max(15)
    @JsonProperty("size")
    private Integer size;

    /** 결과 정렬 순서. distance 정렬을 워할때는 기준좌표로 쓰일 x,y와 함께 사용
     *  X(기본 accuracy)
     **/
    @JsonProperty("sort")
    private String sort;

}
