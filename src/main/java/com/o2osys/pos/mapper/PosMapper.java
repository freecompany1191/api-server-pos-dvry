package com.o2osys.pos.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.o2osys.pos.config.dataSource.Master;

@Mapper
@Master
public interface PosMapper {

    /**
     * 가맹점 검색 목록 출력(CB01_01_V01)
     * @Method Name : bdGetStFindListV01
     * @param map
     */
    void bdGetStFindListV01(Map<String, Object> map);

    /**
     * 만나가맹점 매핑정보 처리(CB01_02_V01)
     * @Method Name : bdModStMappingV01
     * @param map
     */
    void bdModStMappingV01(Map<String, Object> map);

    /**
     * 가맹점 검색 목록 출력(CB01_03_V01)
     * @Method Name : bdGetStInfoV01
     * @param map
     */
    void bdGetStInfoV01(Map<String, Object> map);

    /**
     * 배달요청 주문 전송(CB02_01_V01)
     * @Method Name : bdPutOrdInfoPosV01
     * @param map
     */
    void bdPutOrdInfoPosV01(Map<String, Object> map);

    /**
     * 만나주문 조회(CB02_02_V01)
     * @Method Name : bdGetOrdInfoPosV01
     * @param map
     */
    void bdGetOrdInfoPosV01(Map<String, Object> map);
    void bdGetOrdInfoPosV02(Map<String, Object> map);

    /**
     * 만나주문 목록 조회(CB02_03_V01)
     * @Method Name : bdGetStOrdListV01
     * @param map
     */
    void bdGetStOrdListV01(Map<String, Object> map);
    void bdGetStOrdListV02(Map<String, Object> map);
}
