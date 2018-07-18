package com.o2osys.pos.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.o2osys.pos.config.dataSource.Master;

@Mapper
@Master
public interface ConMapper {

    /**
     * 연동대상 목록 (주문정보)
     * @Method Name : bdGetConList
     * @param map
     */
    void bdGetConList(Map<String, Object> map);

    /**
     * 상태변경 연동대상 목록
     * @Method Name : bdGetConStatusList
     * @param map
     */
    void bdGetConStatusList(Map<String, Object> map);

    /**
     * 만나주문 전송(SD02_01) 연동 상태 처리 (0: 대기중, 1: 전송완료, 2: 전송실패, 3: 전송취소대기(대기중으로 자동변경))
     * 연동 처리 구분에 따른 시간 업데이트  (1: 주문접수, 2: 주문수정, 3: 주문취소)
     *
     * @Method Name : updateConStatus
     * @param map
     * @return
     */
    int updateConStatus(Map<String, Object> map);

    /**
     * 만나주문 상태(SD01_01) 연동 상태 처리 (0: 대기중, 1: 전송완료, 2: 전송실패)
     * @Method Name : updateConStsStatus
     * @param map
     * @return
     */
    int updateConStsStatus(Map<String, Object> map);

    /**
     * 만나주문 전송(SD02_01) 리턴값 업데이트
     * @Method Name : updateSD0201
     * @param map
     * @return
     */
    //int updateSD0201(Map<String, Object> map);
    int updateSD0201(@Param("ORD_NO") String ordNo, @Param("POS_ORD_CODE") String posOrdCode, @Param("POS_ORD_DATE") String posOrdDate);
}
