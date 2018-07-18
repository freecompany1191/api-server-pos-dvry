package com.o2osys.pos.common.location.packet.kakao.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ReqCoord2address.java
   @Description : GET으로 요청 카카오 좌표 -> 주소 변환 REST 파라메터
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
public class ReqCoord2address {

    /** [좌표 -> 행정구역정보 변환], [좌표 -> 주소 변환], [좌표계 변환] =============== */
    /** x 좌표로 경위도인 경우 longitude(필수) */
    @JsonProperty("x")
    private String longitude;

    /** y 좌표로 경위도인 경우 latitude(필수) */
    @JsonProperty("y")
    private String latitude;

    /** x, y 로 입력되는 값에 대한 좌표 체계
     *  X(기본 WGS84)
     *  WGS84 or WCONGNAMUL or CONGNAMUL or WTM or TM
     **/
    @JsonProperty("input_coord")
    private String inputCoord;

    /** 결과에 출력될 좌표 체계
     *  X(기본 WGS84)
     *  WGS84 or WCONGNAMUL or CONGNAMUL or WTM or TM
     **/
    @JsonProperty("output_coord")
    private String outputCoord;

}
