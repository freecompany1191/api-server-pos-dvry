package com.o2osys.pos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2osys.pos.common.constants.Define;
import com.o2osys.pos.common.constants.Define.Lang;
import com.o2osys.pos.common.constants.Define.ProcedureStatus;
import com.o2osys.pos.common.constants.Define.SYSTEM;
import com.o2osys.pos.common.exception.AlreadyCompleteException;
import com.o2osys.pos.common.exception.PosConException;
import com.o2osys.pos.common.location.packet.Location;
import com.o2osys.pos.common.util.CommonUtils;
import com.o2osys.pos.entity.callback.GetOrdInfoPos;
import com.o2osys.pos.entity.callback.GetOrdInfoPos_V02;
import com.o2osys.pos.entity.callback.GetStOrdInfo;
import com.o2osys.pos.entity.callback.GetStOrdInfo_V02;
import com.o2osys.pos.entity.callback.PutOrdInfoPos;
import com.o2osys.pos.entity.callback.ShopInfo;
import com.o2osys.pos.entity.callback.ShopList;
import com.o2osys.pos.entity.rest.AddGoods;
import com.o2osys.pos.entity.rest.Goods;
import com.o2osys.pos.entity.rest.OrdInfo;
import com.o2osys.pos.mapper.PosMapper;
import com.o2osys.pos.packet.callback.ReqPosMessage.ReqPosMsgBody;

/**
   @FileName  : PosCallbackMapperServiceImpl.java
   @Description : POS CALLBACK 맵핑 서비스 구현체
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.   KMS            최초생성

 */
@Service("PosMapperService")
public class PosCallbackMapperServiceImpl {

    // 로그
    private final Logger log = LoggerFactory.getLogger(PosCallbackMapperServiceImpl.class);
    
//    private final String TAG = PosCallbackMapperServiceImpl.class.getSimpleName();

    @Autowired
    PosMapper posMapper;

    /**
     * 가맹점 검색 목록 출력(CB01_01_V01)
     * PKG_SY_CON_POS.BD_GET_ST_FIND_LIST_V01
     * @Method Name : bdGetStFindListV01
     * @param req
     * @return
     * @throws Exception
     */
    protected ArrayList<ShopList> bdGetStFindListV01(ReqPosMsgBody req) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", "TAS");
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** 검색_구분 (1: 가맹점명, 2: 사업자번호, 3: 전화번호, 4: 사업주명) */
        map.put("in_FIND_TYPE", req.getInFindType());

        /** 검색_단어 (검색구분에 따른 검색단어) */
        map.put("in_FIND_VAL", req.getInFindVal());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_ST_FIND_LIST_V01 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB01_01_V01)
        posMapper.bdGetStFindListV01(map);

        log.info("[PKG_SY_CON_POS.BD_GET_ST_FIND_LIST_V01] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_FIND_LIST_V01] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_FIND_LIST_V01] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류) - 9999 리턴
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

        ArrayList<ShopList> shopList = (ArrayList<ShopList>) map.get("out_ROW1");

        log.debug("[PKG_SY_CON_POS.BD_GET_ST_FIND_LIST_V01] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));

        if (shopList == null || shopList.size() <= 0) {
            return null;
        }

        return shopList;
    }

    /**
     * 만나가맹점 매핑정보 처리(CB01_02_V01)
     * PKG_SY_CON_POS.BD_MOD_ST_MAPPING_V01
     * @Method Name : bdModStMappingV01
     * @param req
     * @param posTypeCode
     * @throws Exception
     */
    protected void bdModStMappingV01(ReqPosMsgBody req, String posTypeCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        /** 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...) */
        map.put("in_LANGUAGE", Lang.KO);
        /** 요청한 단말구분 */
        map.put("in_DEVICE", "TAS");
        /** 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅) */
        map.put("in_SERVICE_YN", "Y");

        /** 업체구분코드 */
        map.put("in_POS_TYPE_CD", posTypeCode);

        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", req.getInPosShopCode());

        /** 만나가맹점_코드 */
        map.put("in_SHOP_CODE", req.getInShopCode());

        /** 처리_구분 (1: 등록, 2: 삭제) */
        map.put("in_ACT_TYPE", req.getInActType());

        log.debug("[CALL PKG_SY_CON_POS.BD_MOD_ST_MAPPING_V01 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB01_01_V01)
        posMapper.bdModStMappingV01(map);

        log.info("[PKG_SY_CON_POS.BD_MOD_ST_MAPPING_V01] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_MOD_ST_MAPPING_V01] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_MOD_ST_MAPPING_V01] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {


            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류) - 9999 리턴
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

    }

    /**
     * 가맹점 검색 목록 출력(CB01_03_V01)
     * PKG_SY_CON_POS.BD_GET_ST_INFO_V01
     * @Method Name : bdGetStInfoV01
     * @param req
     * @param posTypeCode
     * @return
     * @throws Exception
     */
    protected ShopInfo bdGetStInfoV01(ReqPosMsgBody req, String posTypeCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", "TAS");
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** 업체구분코드 */
        map.put("in_POS_TYPE_CD", posTypeCode);

        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", req.getInPosShopCode());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_ST_INFO_V01 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB01_01_V01)
        posMapper.bdGetStInfoV01(map);

        log.info("[PKG_SY_CON_POS.BD_GET_ST_INFO_V01] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_INFO_V01] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_INFO_V01] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류) - 9999 리턴
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

        ArrayList<ShopInfo> cursors = (ArrayList<ShopInfo>) map.get("out_ROW1");

        log.debug("[PKG_SY_CON_POS.BD_GET_ST_INFO_V01] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));

        if (cursors == null || cursors.size() <= 0) {
            return null;
        }

        ShopInfo shopInfo = cursors.get(0);

        return shopInfo;
    }


    /**
     * 배달요청 주문 전송(CB02_01_V01)
     * PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01
     * @Method Name : bdPutOrdInfoPosV01
     * @param req
     * @param posTypeCode
     * @return
     * @throws Exception
     */
    protected Map<String, Object> bdPutOrdInfoPosV01(PutOrdInfoPos ins) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //카카오 주소 API 검색 주소
        Location loc = ins.getLocation();

        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", "TAS");
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** 처리_구분 (1: 배달요청, 2: 주문수정, 3: 주문취소) */
        map.put("in_ACT_TYPE", ins.getInActType());

        /** POS_구분_코드 (SY_CODE = 'PT') */
        map.put("in_POS_TYPE_CD", ins.getInPosTypeCd());
        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", ins.getInPosShopCode());
        /** 고객사_주문_코드 (주문일련번호) */
        map.put("in_POS_ORD_CODE", ins.getInPosOrdCode());
        /** 고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우) */
        map.put("in_POS_ORD_DATE", ins.getInPosOrdDate());
        /** 주문_고객_전화번호 */
        map.put("in_ORD_CU_TEL", ins.getInOrdCuTel());

        /** 주문_고객_전화번호_2 (수령고객. 주문고객과 수령고객이 같을 경우 주문_고객_전화번호로 전달) */
        map.put("in_ORD_CU_TEL_2", ins.getInOrdCuTel2());
        /** 주문_메모 */
        map.put("in_ORD_MEMO", ins.getInOrdMemo());
        /** 준비_시간 (단위: 분, 조리시간) */
        map.put("in_READY_TIME", ins.getInReadyTime());

        //주소정보가 있을때만 적용
        if (loc != null) {
            /** 도착_주소_1 (시/도) */
            map.put("in_EA_ADDR_1", loc.getEaAddr1());
            /** 도착_주소_2 (시/군/구) */
            map.put("in_EA_ADDR_2", loc.getEaAddr2());
            /** 도착_주소_3 (읍/면/동/리) */
            map.put("in_EA_ADDR_3", loc.getEaAddr3());
            /** 도착_주소_4 (지번 전체주소) */
            map.put("in_EA_ADDR_4", loc.getEaAddr4());
            /** 도착_주소_5 (사용자 입력주소) */
            map.put("in_EA_ADDR_5", loc.getEaAddr5());
            /** 도착_주소_6 (도로명 전체주소) */
            map.put("in_EA_ADDR_6", loc.getEaAddr6());
            /** 도착_주소_7 (외부연동 전체주소 (문서상의 in_EA_ADDR_JB[in_EA_ADDR_ST] in_EA_ADDR_ETC)) */
            map.put("in_EA_ADDR_7", loc.getEaAddr7());
            /** 도착_주소_8 (행정동) */
            map.put("in_EA_ADDR_8", loc.getEaAddr8());
            /** 도착_주소_9 (도로명) */
            map.put("in_EA_ADDR_9", loc.getEaAddr9());
            /** 도착_주소_10 (건물명) */
            map.put("in_EA_ADDR_10", loc.getEaAddr10());
            /** 도착_주소_11 (지번 (주번-부번)) */
            map.put("in_EA_ADDR_11", loc.getEaAddr11());
            /** 도착_주소_12 (건물번호 (본번-부번)) */
            map.put("in_EA_ADDR_12", loc.getEaAddr12());
            /** 도착_위도_좌표 (Y좌표, LATITUDE) */
            map.put("in_EA_LAT_Y", loc.getEaLatY());
            /** 도착_경도_좌표 (X좌표, LONGITUDE) */
            map.put("in_EA_LNG_X", loc.getEaLngX());
            /** 좌표_정확도_구분 (1: 높음, 2: 중간, 3: 낮음) */
            map.put("in_XY_ACC_TYPE", loc.getXyAccType());
        }

        /** 주문_금액 (총액) */
        map.put("in_ORD_AMT", ins.getInOrdAmt());
        /** 지불_금액 (고객이 결제해야할 금액) */
        map.put("in_PAY_AMT", ins.getInPayAmt());

        /** 결제_구분 (1: 도착지결제, 2: 선결제) */
        map.put("in_CHARGE_TYPE", ins.getInChargeType());
        /** 지불_구분 (1: 현금, 2: 카드) */
        map.put("in_PAY_TYPE", ins.getInPayType());
        /** 상품_이름 (배달상품 간략 설명. 예: 통삼겹1인분 외 1건) */
        map.put("in_GOODS_NAMES", ins.getInGoodsNames());
        /** 취소_사유 */
        map.put("in_CANCEL_MEMO", ins.getInCancelMemo());

        log.debug("[CALL PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB01_01_V01)
        posMapper.bdPutOrdInfoPosV01(map);

        log.info("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] out_CODE is Not Number : "+map.get("out_CODE"));
        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류)
                    throw new Exception(map.get("out_MSG").toString());

            }
        }

        Map<String, Object> resutMap = convertMap(map);

        log.debug("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));

        return resutMap;
    }

    /**
     * 만나주문 조회(CB02_02_V01)
     * PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V01
     * @Method Name : bdGetOrdInfoPosV01
     * @param req
     * @param posTypeCode
     * @return
     * @throws Exception
     */
    protected GetOrdInfoPos bdGetOrdInfoPosV01(ReqPosMsgBody req, String posTypeCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", SYSTEM.TYPE);
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** POS_구분_코드 (SY_CODE = 'PT') */
        map.put("in_POS_TYPE_CD", posTypeCode);

        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", req.getInPosShopCode());

        /** 고객사_주문_코드 (주문일련번호) */
        map.put("in_POS_ORD_CODE", req.getInPosOrdCode());

        /** 고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우) */
        map.put("in_POS_ORD_DATE", req.getInPosOrdDate());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V01 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB02_02_V01)
        posMapper.bdGetOrdInfoPosV01(map);

        log.info("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V01] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V01] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V01] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류)
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

        ArrayList<GetOrdInfoPos> cursors = (ArrayList<GetOrdInfoPos>) map.get("out_ROW1");

        log.debug("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V01] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));

        if (cursors == null || cursors.size() <= 0) {
            return null;
        }

        GetOrdInfoPos ordInfo = cursors.get(0);

        return ordInfo;
    }
    
    /**
     * 만나주문 조회(CB02_02_V02)
     * PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02
     * @Method Name : bdGetOrdInfoPosV02
     * @param req
     * @param posTypeCode
     * @return
     * @throws Exception
     */
    protected GetOrdInfoPos_V02 bdGetOrdInfoPosV02(ReqPosMsgBody req, String posTypeCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", SYSTEM.TYPE);
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** POS_구분_코드 (SY_CODE = 'PT') */
        map.put("in_POS_TYPE_CD", posTypeCode);

        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", req.getInPosShopCode());

        /** 고객사_주문_코드 (주문일련번호) */
        map.put("in_POS_ORD_CODE", req.getInPosOrdCode());

        /** 고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우) */
        map.put("in_POS_ORD_DATE", req.getInPosOrdDate());
        
        /** 만나_주문_코드 */
        map.put("in_ORD_NO", req.getInOrdNo());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB02_02_V01)
        posMapper.bdGetOrdInfoPosV02(map);

        log.info("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류)
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

        ArrayList<OrdInfo> cursors = (ArrayList) map.get("out_ROW1");

        log.debug("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));
        log.debug("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02] out_ROW2 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW2")));
        log.debug("[PKG_SY_CON_POS.BD_GET_ORD_INFO_POS_V02] out_ROW3 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW3")));

        if (cursors == null || cursors.size() <= 0) {
            return null;
        }
        
        GetOrdInfoPos_V02 ordInfo = new GetOrdInfoPos_V02(cursors.get(0));
        //주문상품 목록
        ArrayList<Goods> goods = (ArrayList<Goods>) map.get("out_ROW2");
        //주문추가상품 목록
        ArrayList<AddGoods> addGoods = (ArrayList<AddGoods>) map.get("out_ROW3");
        
		if (ordInfo != null) {
			// 임시 주문상품 목록 변수 초기화
			ArrayList<com.o2osys.pos.packet.v02.rest.InGoodsData> tempGoods = new ArrayList<>();
			for (Goods good : goods) {

				if (good != null) {
					// 임시 주문추가상품 목록 변수 초기화
                    ArrayList<AddGoods> tempAddGoods = new ArrayList<>();
                    //주문추가상품 목록 루프문시작
					for (AddGoods addGood : addGoods) {

						if (addGood != null) {
                            //POS_상품_코드, 주문_상품_일련번호가 일치한 주문추가상품을 임시 주문추가상품 목록에 추가
							if ((good.getOrdGoodsNo() == addGood.getOrdGoodsNo())
									&& good.getPosGoodsCode().equals(addGood.getPosAddGoodsCode())) {
                                log.debug("ordNo : "+req.getInOrdNo()+", "
                                        + "posGoodsCode : "+good.getPosGoodsCode() +", "
                                        + "ordGoodsNo : "+good.getOrdGoodsNo()+", "
                                        + "addGood : "+addGood.toString());
                                tempAddGoods.add(addGood);
                            }//end if
                        } //end if
                    }//end for
                    //루프문을 끝내고 담아진 주문추가상품 목록을 주문상품에 셋팅
                    good.setAddGoods(tempAddGoods);
                    //담아진 주문상품을 주문상품 목록에 추가
                    log.debug("ordNo : "+req.getInOrdNo()+", good : "+good.toString());
                    tempGoods.add(new com.o2osys.pos.packet.v02.rest.InGoodsData(good));
                }//end if
            }//end for
            //루프문을 끝내고 담아진 주문상품 목록을 연동대상에 셋팅
			ordInfo.setGoods(tempGoods);
            //담아진 연동대상을 연동대상 목록에 추가
        }//end if
		log.debug("ordInfo : "+ordInfo.toString());

        return ordInfo;
    }

    /**
     * 만나주문 조회(CB02_03_V01)
     * PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V01
     * @Method Name : bdGetStOrdListV01
     * @param req
     * @param posTypeCode
     * @return
     * @throws Exception
     */
    protected ArrayList<GetStOrdInfo> bdGetStOrdListV01(ReqPosMsgBody req, String posTypeCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", SYSTEM.TYPE);
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** POS_구분_코드 (SY_CODE = 'PT') */
        map.put("in_POS_TYPE_CD", posTypeCode);

        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", req.getInPosShopCode());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V01 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB02_02_V01)
        posMapper.bdGetStOrdListV01(map);

        log.info("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V01] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V01] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V01] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류)
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

        ArrayList<GetStOrdInfo> cursors = (ArrayList<GetStOrdInfo>) map.get("out_ROW1");

        log.debug("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V01] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));

        if (cursors == null || cursors.size() <= 0) {
            return null;
        }

        return cursors;
    }
    
    /**
     * 만나주문 조회(CB02_03_V02)
     * PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V02
     * @Method Name : bdGetStOrdListV02
     * @param req
     * @param posTypeCode
     * @return
     * @throws Exception
     */
    protected GetStOrdInfo_V02 bdGetStOrdListV02(ReqPosMsgBody req, String posTypeCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", SYSTEM.TYPE);
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        /** POS_구분_코드 (SY_CODE = 'PT') */
        map.put("in_POS_TYPE_CD", posTypeCode);

        /** 고객사_가맹점_코드 */
        map.put("in_POS_SHOP_CODE", req.getInPosShopCode());
        
        /** 마지막싱크데이트 */
        map.put("in_LAST_SYNC_DATE", req.getInLastSyncDate());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V02 JSON : "+CommonUtils.jsonStringFromObject(map));

        //가맹점 검색 목록 출력(CB02_02_V01)
        posMapper.bdGetStOrdListV02(map);

        log.info("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V02] out_CODE : "+map.get("out_CODE")+", out_MSG : "+map.get("out_MSG"));

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V02] out_CODE is Null : "+map.get("out_CODE"));

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V02] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {

            int outCode = (int) map.get("out_CODE");

            switch(outCode){
                case ProcedureStatus.Code.FAIL :
                    // 처리거절(처리 불가능 상황) - 0002 리턴
                    throw new PosConException(map.get("out_MSG").toString());
                case ProcedureStatus.Code.DUPLE :
                    // 기 처리상태(이미 처리된 상태) - 0003 리턴
                    throw new AlreadyCompleteException(map.get("out_MSG").toString());
                default :
                    // 시스템 오류(정의되지 않은 오류)
                    throw new Exception(map.get("out_MSG").toString());
            }
        }

        ArrayList<OrdInfo> cursors = (ArrayList) map.get("out_ROW1");
        
        log.debug("[PKG_SY_CON_POS.BD_GET_ST_ORD_LIST_V02] out_ROW1 : "+CommonUtils.jsonStringFromObject(map.get("out_ROW1")));

        if (cursors == null || cursors.size() <= 0) {
            return null;
        }
        
        GetStOrdInfo_V02 stOrdInfo = new GetStOrdInfo_V02();
        stOrdInfo.setLastSyncDate((String) map.get("out_VALUE"));
        
        ArrayList<com.o2osys.pos.packet.v02.rest.inStOrdData> tempStOrdData = new ArrayList();
        for (int i = 0; i < cursors.size(); i++) {
        	tempStOrdData.add(new com.o2osys.pos.packet.v02.rest.inStOrdData(cursors.get(i)));
		}
        stOrdInfo.setStOrdList(tempStOrdData);

        return stOrdInfo;
    }

    /**
     * 프로시저 결과값 컨버팅 String
     * @Method Name : convertStr
     * @param object
     * @return
     * @throws Exception
     */
    private String convertStr(Object object, String key) throws Exception {
        if (object == null) {
            return null;
        }

        if (StringUtils.isEmpty(key)) {
            return null;
        }

        ArrayList<LinkedHashMap<String, Object>> rows = (ArrayList<LinkedHashMap<String, Object>>) object;

        if (rows == null || rows.size() <= 0) {
            return null;
        }

        if ("NULL".equals((rows.get(0).keySet().iterator().next()))) {
            return null;
        }

        String result = null;

        LinkedHashMap<String, Object> resultMap = rows.get(0);

        Iterator<String> iterator = resultMap.keySet().iterator();

        String keyName;

        while (iterator.hasNext()) {
            keyName = iterator.next();

            log.info("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] KEYNAME : "+ keyName);
            if(key.equals(keyName)){
                if (resultMap.get(keyName) != null)
                    result = String.valueOf(resultMap.get(keyName));
            }

        }

        return result;
    }

    /**
     * 프로시저 결과값 컨버팅 Map
     * @Method Name : convertMap
     * @param map
     * @return
     * @throws Exception
     */
    private Map<String, Object> convertMap(Map<String, Object> map) throws Exception {
        if (map == null) {
            return null;
        }

        if (map.get("out_ROW1") == null) {
            return null;
        }

        ArrayList<LinkedHashMap<String, Object>> rows = (ArrayList<LinkedHashMap<String, Object>>) map.get("out_ROW1");

        if (rows == null || rows.size() <= 0) {
            return null;
        }

        if ("NULL".equals((rows.get(0).keySet().iterator().next()))) {
            return null;
        }

        LinkedHashMap<String, Object> resultMap = rows.get(0);

        Iterator<String> iterator = resultMap.keySet().iterator();

        String keyName;

        while (iterator.hasNext()) {
            keyName = iterator.next();

            log.debug("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] KEYNAME : "+ keyName);

            if (resultMap.get(keyName) != null){

                log.info("[PKG_SY_CON_POS.BD_PUT_ORD_INFO_POS_V01] KEY : "+ keyName+", VALUE : "+resultMap.get(keyName));
                map.put(keyName, String.valueOf(resultMap.get(keyName)));

            }

        }

        return map;
    }
}
