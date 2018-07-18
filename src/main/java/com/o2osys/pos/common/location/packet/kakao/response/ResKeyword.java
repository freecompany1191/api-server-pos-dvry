package com.o2osys.pos.common.location.packet.kakao.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
   @FileName  : ResKeyword.java
   @Description : 키워드로 장소 검색
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
public class ResKeyword {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private ArrayList<Documents> documents;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        /** 매칭된 문서수 */
        @JsonProperty("total_count")
        private Integer totalCount;

        /** total_count 중에 노출가능 문서수. 최대 45. */
        @JsonProperty("pageable_count")
        private Integer pageableCount;

        /** 현재 페이지가 마지막 페이지인지 여부. 값이 false이면 page를 증가시켜 다음 페이지를 요청할 수 있음. */
        @JsonProperty("is_end")
        private boolean isEnd;

        /** 질의어의 지역/키워드 분석 정보 */
        @JsonProperty("same_name")
        private Samename samename;

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Samename {
            /** 질의어에서 인식된 지역의 리스트. '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트 */
            @JsonProperty("region")
            private String[] region;

            /** 질의어에서 지역 정보를 제외한 키워드. '중앙로 맛집' 에서 '맛집' */
            @JsonProperty("keyword")
            private String keyword;

            /** 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보. */
            @JsonProperty("selected_region")
            private String selectedRegion;
        }

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Documents {

        /** 장소 ID */
        @JsonProperty("id")
        private String id;

        /** 장소명, 업체명 */
        @JsonProperty("place_name")
        private String placeName;

        /** 카테고리 이름 */
        @JsonProperty("category_name")
        private String categoryName;

        /** 중요 카테고리만 그룹핑한 카테고리 그룹 코드. Request에 category_group_code 테이블 참고 */
        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        /** 전화번호 */
        @JsonProperty("phone")
        private String phone;

        /** 전체 지번 주소 */
        @JsonProperty("address_name")
        private String addressName;

        /** 전체 도로명 주소 */
        @JsonProperty("road_address_name")
        private String roadAddressName;

        /** X 좌표값 혹은 longitude */
        @JsonProperty("x")
        private String longitude;

        /** Y 좌표값 혹은 latitude */
        @JsonProperty("y")
        private String latitude;

        /** 장소 상세페이지 URL */
        @JsonProperty("place_url")
        private String placeUrl;

        /** 중심좌표까지의 거리(x,y 파라미터를 준 경우에만 존재). 단위 meter */
        @JsonProperty("distance")
        private String distance;
    }
}
