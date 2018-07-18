package com.o2osys.pos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2osys.pos.common.constants.Define;
import com.o2osys.pos.common.constants.Define.Lang;
import com.o2osys.pos.common.constants.Define.SYSTEM;
import com.o2osys.pos.common.exception.DBCheckException;
import com.o2osys.pos.common.exception.PosConException;
import com.o2osys.pos.common.util.CommonUtils;
import com.o2osys.pos.entity.rest.AddGoods;
import com.o2osys.pos.entity.rest.ConInfo;
import com.o2osys.pos.entity.rest.ConStatusInfo;
import com.o2osys.pos.entity.rest.Goods;
import com.o2osys.pos.mapper.ConMapper;
import com.o2osys.pos.packet.sky.rest.ResSkyCommon.ResSkyCommonBody;

/**
   @FileName  : PosScheduledMapperServiceImpl.java
   @Description : POS 스케쥴링 맵퍼 서비스 구현체
   @author      : KMS
   @since       : 2017. 10. 23.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 23.     KMS            최초생성

 */
@Service("PosScheduledMapperService")
public class PosScheduledMapperServiceImpl {
    // 로그
    private final Logger log = LoggerFactory.getLogger(PosScheduledMapperServiceImpl.class);
    private final String TAG = PosScheduledMapperServiceImpl.class.getSimpleName();

    @Autowired
    ConMapper conMapper;

    private ObjectMapper mObjectMapper = new ObjectMapper();

    //연동대상 목록 버전
    @Value("${pos.conlist.version}")
    String version;


    /**
     * 연동대상 목록 (주문정보)
     * @Method Name : bdGetConList
     * @return
     * @throws Exception
     */
    public ArrayList<ConInfo> bdGetConList() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        // 프로시저 버전
        map.put("in_VERSION", version);
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", SYSTEM.TYPE);
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_CON_LIST_"+version+"] REQ MAP JSON : "+CommonUtils.jsonStringFromObject(map));

        //연동대상 목록 (주문정보)
        try {
            conMapper.bdGetConList(map);
        } catch (RecoverableDataAccessException e) {
            throw new DBCheckException("[REST GET_CON_LIST] DB 변경 작업중 :: "+e.getMessage());
        } catch (UncategorizedSQLException e) {
            throw new DBCheckException("[REST GET_CON_LIST] DB 프로시져 변경 작업중 :: "+e.getMessage());
        }

        if(map.get("out_CODE") == null){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_CON_LIST_"+version+"] out_CODE is Null");

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("[PKG_SY_CON_POS.BD_GET_CON_LIST_"+version+"] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {
            throw new PosConException(map.get("out_MSG").toString());
        }

        //연동대상 목록
        ArrayList<ConInfo> conList = (ArrayList<ConInfo>) map.get("out_ROW1");
        if(conList == null){
            throw new DBCheckException("[REST GET_CON_LIST] 04시50분 - 05시00분 일일정산 처리 시간");
        }

        //주문상품 목록
        ArrayList<Goods> goods = (ArrayList<Goods>) map.get("out_ROW2");
        //주문추가상품 목록
        ArrayList<AddGoods> addGoods = (ArrayList<AddGoods>) map.get("out_ROW3");

        //log.debug("# conList.size() : " +conList.size() +", goods.size() : "+ goods.size()+", addGoods.size() : "+addGoods.size());

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_CON_LIST_"+version+"] CON_LIST JSON : "+CommonUtils.jsonStringFromObject(map));

        int a=0;
        int b=0;
        int c=0;

        ArrayList<ConInfo> newConList = new ArrayList<ConInfo>();

        for(ConInfo con : conList){

            if(con != null){
                //임시 주문상품 목록 변수 초기화
                ArrayList<Goods> tempGoods = new ArrayList<Goods>();
                for(Goods good : goods){

                    if(good != null){
                        //연동대상 목록 주문 일련번호와 주문 상품 일련번호가 일치한 주문 상품을 임시 주문상품 목록에 추가
                        if(con.getOrdNo().equals("" + good.getOrdNo())){

                            //임시 주문추가상품 목록 변수 초기화
                            ArrayList<AddGoods> tempAddGoods = new ArrayList<AddGoods>();
                            //주문추가상품 목록 루프문시작
                            for(AddGoods addGood : addGoods){

                                if(addGood != null){
                                    //주문 일련번호, POS_상품_코드, 주문_상품_일련번호가 일치한 주문추가상품을 임시 주문추가상품 목록에 추가
                                    if( (good.getOrdNo() == addGood.getOrdNo())
                                            && good.getPosGoodsCode().equals(addGood.getPosAddGoodsCode())
                                            && (good.getOrdGoodsNo() == addGood.getOrdGoodsNo())){
                                        log.debug("["+a+"]["+b+"]["+c+"] ordNo : "+good.getOrdNo()+", "
                                                + "posGoodsCode : "+good.getPosGoodsCode() +", "
                                                + "ordGoodsNo : "+good.getOrdGoodsNo()+", "
                                                + "addGood : "+addGood.toString());
                                        tempAddGoods.add(addGood);
                                    }//end if
                                } //end if
                                c++;

                            }//end for
                            //루프문을 끝내고 담아진 주문추가상품 목록을 주문상품에 셋팅
                            good.setAddGoods(tempAddGoods);
                            //담아진 주문상품을 주문상품 목록에 추가
                            log.debug("["+a+"]["+b+"] ordNo : "+good.getOrdNo()+", good : "+good.toString());
                            tempGoods.add(good);
                        }//end if
                    }//end if
                    b++;

                }//end for
                //루프문을 끝내고 담아진 주문상품 목록을 연동대상에 셋팅
                con.setGoods(tempGoods);
                //담아진 연동대상을 연동대상 목록에 추가
                log.debug("["+a+"] conInfo : "+con.toString());
                newConList.add(con);
            }//end if
            a++;

        }//end for

        log.debug("[CALL PKG_SY_CON_POS.BD_GET_CON_LIST_"+version+"] RES CONLIST JSON : "+CommonUtils.jsonStringFromObject(newConList));
        /*
        if (cursors == null || cursors.size() <= 0) {
            return null;
        }
         */

        return newConList;
    }

    /**
     * 주문배달 연동 상태 처리
     *
     * @Method Name : updateConStatus
     * @param con
     * @param status
     * @throws Exception
     */
    public void updateConStatus(ConInfo con, String status) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("ORD_NO", con.getOrdNo());
        map.put("POS_TYPE_CD", con.getPosTypeCd());
        map.put("CON_STATUS", status);
        map.put("CON_ACT_TYPE", con.getConActType());

        log.debug("[UPDATE_CON_STATUS] REQ MAP JSON : "+CommonUtils.jsonStringFromObject(map));

        //POS 연동 상태 처리 (0: 대기중, 1: 전송완료, 2: 전송실패, 3: 전송취소대기(대기중으로 자동변경))
        conMapper.updateConStatus(map);
    }


    /**
     * 상태변경 연동대상 목록 조회
     * @Method Name : bdGetConStatusList
     * @return
     * @throws Exception
     */
    public ArrayList<ConStatusInfo> bdGetConStatusList() throws Exception{
        String packageNm = "PKG_SY_CON_POS.BD_GET_CON_STATUS_LIST_";
        String procedureNm = "BD_GET_CON_STATUS_LIST";

        Map<String, Object> map = new HashMap<String, Object>();

        // 프로시저 버전
        map.put("in_VERSION", version);
        // 언어구분 (MN_SY_CODE.SY_CODE = 'LT', 0001:한국어...)
        map.put("in_LANGUAGE", Lang.KO);
        // 요청한 단말구분
        map.put("in_DEVICE", SYSTEM.TYPE);
        // 실제사용 정보 출력 Y/N (제휴본사, 총판, 가맹점 정보 등, API 서버를 서비스/테스트로 별도 운영하여 구분하여 세팅)
        map.put("in_SERVICE_YN", "Y");

        log.debug("[CALL "+packageNm+version+"] REQ MAP JSON : "+CommonUtils.jsonStringFromObject(map));

        //연동대상 목록 (주문정보)
        try {
            conMapper.bdGetConStatusList(map);
        } catch (RecoverableDataAccessException e) {
            throw new DBCheckException("[REST "+procedureNm+"] DB 변경 작업중 :: "+e.getMessage());
        } catch (UncategorizedSQLException e) {
            throw new DBCheckException("[REST "+procedureNm+"] DB 프로시져 변경 작업중 :: "+e.getMessage());
        }

        if(map.get("out_CODE") == null){

            throw new Exception("["+packageNm+version+"] out_CODE is Null");

        }else if(!NumberUtils.isDigits( String.valueOf(map.get("out_CODE")).trim() )){

            throw new Exception("["+packageNm+version+"] out_CODE is Not Number : "+map.get("out_CODE"));

        }

        if (Define.ProcedureStatus.Code.OK != (int) map.get("out_CODE")) {
            throw new PosConException(map.get("out_MSG").toString());
        }

        //연동대상 목록
        ArrayList<ConStatusInfo> conStatusList = (ArrayList<ConStatusInfo>) map.get("out_ROW1");

        if(conStatusList == null){
            throw new DBCheckException("[REST "+procedureNm+"] 04시50분 - 05시00분 일일정산 처리 시간");
        }

        log.debug("[CALL "+packageNm+version+"] CON_STATUS_LIST JSON : "+CommonUtils.jsonStringFromObject(map));

        return conStatusList;

    }

    /**
     * 주문배달 상태변경 연동 상태 처리
     * @Method Name : updateConStsStatus
     * @param con
     * @param status
     * @throws Exception
     */
    public void updateConStsStatus(ConStatusInfo con, String status) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("CON_SEQ_NO", con.getConSeqNo());
        map.put("CON_STATUS", status);

        log.debug("[UPDATE_CON_STS_STATUS] REQ MAP JSON : "+CommonUtils.jsonStringFromObject(map));

        //POS 상태변경 연동 상태 처리 (0: 대기중, 1: 전송완료, 2: 전송실패)
        conMapper.updateConStsStatus(map);
    }

    /**
     * 만나주문 전송(SD02_01) 리턴값 업데이트
     * @Method Name : updateSD0201
     * @param res
     * @throws Exception
     */
    public void updateSD0201(ResSkyCommonBody res) throws Exception {
        //Map<String, Object> map = new HashMap<String, Object>();
        //map.put("in_ORD_NO", String.valueOf(res.getOrdNo()));              //만나주문_일련번호
        //map.put("in_POS_ORD_CODE ", String.valueOf(res.getPosOrdCode()));  //고객사_가맹점_코드
        //map.put("in_POS_ORD_DATE ", String.valueOf(res.getPosOrdDate()));  //고객사_주문_코드 (주문일련번호)
        //map.put("in_POS_SHOP_CODE", String.valueOf(res.getPosShopCode())); //고객사_주문_일자 (YYYYMMDD. 주문일련번호가 일자별일 경우)

        //log.info("[UPDATE_SD02_01] REQ MAP JSON : "+CommonUtils.jsonStringFromObject(map));

        //conMapper.updateSD0201(map);

        //오류로 인해 아래로직으로 대체
        String ordNo = res.getOrdNo();           //만나주문_일련번호
        String posOrdCode = res.getPosOrdCode(); //고객사_가맹점_코드
        String posOrdDate = res.getPosOrdDate(); //고객사_주문_코드 (주문일련번호)

        conMapper.updateSD0201( ordNo, posOrdCode, posOrdDate );
    }

}