package com.o2osys.pos.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2osys.pos.common.constants.Const;
import com.o2osys.pos.common.constants.Const.RES;
import com.o2osys.pos.common.constants.Const.SERVICE;
import com.o2osys.pos.common.constants.Define;
import com.o2osys.pos.common.constants.Define.SYSTEM;
import com.o2osys.pos.common.constants.Define.XyAccType;
import com.o2osys.pos.common.exception.AlreadyCompleteException;
import com.o2osys.pos.common.exception.AuthKeyException;
import com.o2osys.pos.common.exception.PosConException;
import com.o2osys.pos.common.exception.RequestException;
import com.o2osys.pos.common.location.packet.Location;
import com.o2osys.pos.common.location.service.LocationRestService;
import com.o2osys.pos.common.service.CommonService;
import com.o2osys.pos.common.service.LogService;
import com.o2osys.pos.common.util.PosUtils;
import com.o2osys.pos.entity.callback.GetOrdInfoPos;
import com.o2osys.pos.entity.callback.GetOrdInfoPos_V02;
import com.o2osys.pos.entity.callback.GetStOrdInfo;
import com.o2osys.pos.entity.callback.GetStOrdInfo_V02;
import com.o2osys.pos.entity.callback.PutOrdInfoPos;
import com.o2osys.pos.entity.callback.ShopInfo;
import com.o2osys.pos.entity.callback.ShopList;
import com.o2osys.pos.packet.ResCommon;
import com.o2osys.pos.packet.callback.ReqPosMessage;
import com.o2osys.pos.packet.callback.ReqPosMessage.ReqPosMsgBody;
import com.o2osys.pos.packet.callback.ReqPosMessage.ReqPosMsgHeader;
import com.o2osys.pos.packet.callback.ResPosBody;
import com.o2osys.pos.packet.callback.ResPosHeader;
import com.o2osys.pos.packet.callback.ResPosMessage;
import com.o2osys.pos.packet.callback.ResPosShopList;
import com.o2osys.pos.service.PosCallbackService;

/**
   @FileName  : PosServiceImpl.java
   @Description : POS 요청 처리 서비스
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성

 */
@Service
public class PosCallbackServiceImpl implements PosCallbackService {
    // 로그
    private final Logger log = LoggerFactory.getLogger(PosCallbackServiceImpl.class);
    private final String TAG = PosCallbackServiceImpl.class.getSimpleName();

    /** 공통서비스 */
    @Autowired
    CommonService commonService;

    @Autowired
    LogService logService;

    //카카오 주소 API 서비스 REST 방식
    @Autowired
    LocationRestService mLocationRestService;

    @Autowired
    PosCallbackMapperServiceImpl posMapperService;

    ObjectMapper mObjectMapper = new ObjectMapper();

    @Value("${test.mode}")
    boolean test_mode; //테스트 모드 여부

    @Value("${unitas.callback.apikey}")
    String UNITAS_APIKEY; //유니타스

    @Value("${spc.callback.apikey}")
    String SPC_APIKEY; //SPC

    @Value("${sky.callback.apikey}")
    String SKY_APIKEY; //SKY포스

    @Value("${easy.callback.apikey}")
    String EASY_APIKEY; //EASY포스
    
    @Value("${solbi.callback.apikey}")
    String SOLBI_APIKEY; //솔비포스

    @Value("${okpos.callback.apikey}")
    String OKPOS_APIKEY; //OK포스

    /**
     * POS 요청 처리 서비스
     * @Method Name : getResPosMessage
     * @param reqPosMessage
     * @return
     * @throws Exception
     */
    @Override
    public ResPosMessage getResPosMessage(ReqPosMessage req) throws Exception {

        ResPosHeader resHeader = new ResPosHeader();
        ResPosMessage res = new ResPosMessage();
        Object resBody = new Object();

        ReqPosMsgHeader reqHeader =  req.getHeader();
        ReqPosMsgBody reqBody = req.getBody();

        String key = reqHeader.getKey();
        String traceNo = reqHeader.getTraceNo();
        String serviceCode = reqHeader.getServiceCode();
        String posTypeCode = reqHeader.getPosTypeCode();
        
        resHeader.setServiceCode(serviceCode);

        try {

            RequestValueCheck(key,"KEY"); //발행받은 인증키 값
            RequestValueCheck(traceNo,"TRACE_NO"); //송수신 일련번호
            RequestValueCheck(serviceCode,"SERVICE_CODE"); //요청전문번호
            RequestValueCheck(posTypeCode,"POS_TYPE_CODE"); //업체구분코드

            //업체구분코드에 따른 서비스 분기처리
            switch(posTypeCode){

                //키값이 틀리면 키인증 에러
                case Const.POS_TYPE_CODE.UNITAS :
                    if(!key.equals(UNITAS_APIKEY)) throw new AuthKeyException(RES.MSG.AUTH_ERROR);
                    break;

                case Const.POS_TYPE_CODE.SPC :
                    if(!key.equals(SPC_APIKEY)) throw new AuthKeyException(RES.MSG.AUTH_ERROR);
                    break;

                case Const.POS_TYPE_CODE.SKY :
                    if(!key.equals(SKY_APIKEY)) throw new AuthKeyException(RES.MSG.AUTH_ERROR);
                    break;

                case Const.POS_TYPE_CODE.EASY :
                    if(!key.equals(EASY_APIKEY)) throw new AuthKeyException(RES.MSG.AUTH_ERROR);
                    break;
                    
                case Const.POS_TYPE_CODE.SOLBI :
                    if(!key.equals(SOLBI_APIKEY)) throw new AuthKeyException(RES.MSG.AUTH_ERROR);
                    break;
                    
                case Const.POS_TYPE_CODE.OKPOS :
                    if(!key.equals(OKPOS_APIKEY)) throw new AuthKeyException(RES.MSG.AUTH_ERROR);
                    break;
                    
                default :
                    throw new AuthKeyException(RES.MSG.AUTH_ERROR);
            }

            //서비스 코드를 코드와 버전으로 분리하여 담는다
            Map<String, Object> serviceMap = PosUtils.getVersion(serviceCode);
            serviceCode = (String) serviceMap.get("SERVICE_CODE");
            String version = (String) serviceMap.get("VERSION");

            String ActType = "";
            //배달요청 주문 전송 (CB02_01) 일 경우 전문 구분에 따른 정보표시
            if(SERVICE.CODE.CB02._01.equals(serviceCode))
                ActType = reqBody.getInActType()+" : "+PosUtils.CodeToName("CB02_01",reqBody.getInActType());

            log.info("["+PosUtils.CodeToName("POS_TYPE_CODE",posTypeCode)+"] "
                    +"["+serviceCode+"_"+version+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCode)+") "
                    + ActType +"] 호출");

            //서비스 코드에 따른 분기처리
            switch(serviceCode){

                case SERVICE.CODE.CB01._01 : //만나가맹점 검색 (CB01_01)
                    RequestValueCheck(reqBody.getInFindVal(),"in_FIND_TYPE"); //검색_구분 누락 체크
                    RequestValueCheck(reqBody.getInFindVal(),"in_FIND_VAL"); //검색_단어 누락 체크
                    ArrayList<ShopList> shopList = posMapperService.bdGetStFindListV01(reqBody);

                    if(shopList != null){
                        resBody = new ResPosShopList(shopList);
                    }else{
                        resBody = new ResCommon();
                        ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                    }

                    break;
                case SERVICE.CODE.CB01._02 : //고객사 가맹점 정보 등록/삭제 (CB01_02)
                    RequestValueCheck(reqBody.getInPosShopCode(),"in_POS_SHOP_CODE"); //고객사_가맹점_코드 누락 체크
                    RequestValueCheck(reqBody.getInShopCode(),"in_SHOP_CODE"); //만나가맹점_코드 누락 체크
                    RequestValueCheck(reqBody.getInActType(),"in_ACT_TYPE"); //처리_구분 누락 체크
                    posMapperService.bdModStMappingV01(reqBody, posTypeCode);
                    resBody = new ResCommon();
                    break;
                case SERVICE.CODE.CB01._03 : //만나가맹점 정보조회 (CB01_03)
                    RequestValueCheck(reqBody.getInPosShopCode(),"in_POS_SHOP_CODE"); //고객사_가맹점_코드 누락 체크
                    ShopInfo shopInfo = posMapperService.bdGetStInfoV01(reqBody, posTypeCode);

                    if(shopInfo != null){
                        resBody = new ShopInfo(shopInfo);
                    }else{
                        resBody = new ResCommon();
                        ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                    }
                    break;
                case SERVICE.CODE.CB02._01 : //배달요청 주문 전송 (CB02_01)

                    RequestValueCheck(reqBody.getInActType(),    "in_ACT_TYPE");      //처리_구분 (1: 배달요청, 2: 주문수정)
                    RequestValueCheck(reqBody.getInPosShopCode(),"in_POS_SHOP_CODE"); //고객사_가맹점_코드
                    RequestValueCheck(reqBody.getInPosOrdCode(), "in_POS_ORD_CODE");  //고객사_주문_코드 (주문일련번호)

                    //처리 구분이 3. 주문취소가 아닐때만 필수
                    if(!reqBody.getInActType().equals(Const.SERVICE.CODE.CB02._01_CODE.CANCEL)){
                        RequestValueCheck(reqBody.getInChargeType(), "in_CHARGE_TYPE");   //결제_구분 (1: 도착지결제, 2: 선결제)
                        RequestValueCheck(reqBody.getInPayType(),    "in_PAY_TYPE");      //지불_구분 (1: 현금, 2: 카드)
                    }
                    //RequestValueCheck(reqBody.getInGoodsNames(), "in_GOODS_NAMES");   //상품_이름 (배달상품 간략 설명. 예: 통삼겹1인분 외 1건)


                    Location location = null;

                    //배달요청 주문 전송(주문취소) 가 아닐경우에만 적용
                    if (!"3".equals(reqBody.getInActType())) {
                        //도로명이 있으면 도로명으로 조회
                        if (!StringUtils.isEmpty(reqBody.getInEaAddrSt())) {
                            //카카오 주소검색 API 로 조회 및 패턴이 적용된 주소 적용
                            location = mLocationRestService.getLocation(SYSTEM.TYPE, reqBody.getInEaAddrSt());
                        }
                        //결과 정보가 없고 지번주소가 있으면 지번으로 다시 조회
                        if (location == null) {

                            if (!StringUtils.isEmpty(reqBody.getInEaAddrJb())) {
                                //카카오 주소검색 API 로 조회 및 패턴이 적용된 주소 적용
                                location = mLocationRestService.getLocation(SYSTEM.TYPE, reqBody.getInEaAddrJb());
                            }
                        }
                        //POS 요청 주소 셋팅
                        String address = reqBody.getInEaAddrJb() + "[" + reqBody.getInEaAddrSt() + "]"
                                + reqBody.getInEaAddrEtc();
                        address = address.trim();
                        //최종 결과가 없으면 주소 없음 처리 후 좌표 낮음 처리
                        if (location == null) {
                            logService.error(Define.Log.TYPE, TAG,
                                    "[" + reqBody.getInPosShopCode()+", " +reqBody.getInShopCode()+ "] Location is null ADDR4 : " + address);
                            log.info("POS Location is Null = 위치값을 가져올 수 없음 좌표정확도 낮음 처리. ADDR4[" + address + "]");
                            //API 위치값을 가져올 수 없을 때 좌표 정확도 낮음으로 셋팅 후 계속 진행 2017-08-28
                            location = new Location();
                            location.setXyAccType(XyAccType.TYPE_3);
                        }
                        location.setEaAddr7(address);
                    }

                    //배달요청 주문 전송 요청 형식으로 변환
                    PutOrdInfoPos putOrdInfo = new PutOrdInfoPos(reqBody, posTypeCode, location);

                    //배달요청 주문 전송 프로시저 호출
                    Map<String, Object> resultMap =  posMapperService.bdPutOrdInfoPosV01(putOrdInfo);

                    String resMsg = String.valueOf(resultMap.get("out_MSG"));
                    if(!StringUtils.isEmpty(resMsg) && !resMsg.equals("null")){
                        resHeader.setResMsg(resMsg);
                    }

                    String ordNo = String.valueOf(resultMap.get("ORD_NO"));
                    String dvryAmt = String.valueOf(resultMap.get("DVRY_AMT"));

                    if(!StringUtils.isEmpty(ordNo)){
                        resBody = new ResPosBody(ordNo, dvryAmt);
                    }else{
                        resBody = new ResCommon();
                        ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                    }
                    break;
                case SERVICE.CODE.CB02._02 : //만나주문 조회 (CB02_02)
                    RequestValueCheck(reqBody.getInPosShopCode(),"in_POS_SHOP_CODE"); //고객사_가맹점_코드 누락 체크
                    RequestValueCheck(reqBody.getInPosOrdCode(), "in_POS_ORD_CODE");  //고객사_주문_코드 (주문일련번호)

                    //만나주문 조회 프로시저 호출
                    if (version.equals("V01")) {
                    	GetOrdInfoPos getOrdInfo = posMapperService.bdGetOrdInfoPosV01(reqBody, posTypeCode);
                    	if(getOrdInfo != null){
                            resBody = getOrdInfo;
                        }else{
                            resBody = new ResCommon();
                            ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                        }
                    } else if (version.equals("V02")) {
                    	GetOrdInfoPos_V02 getOrdInfo = posMapperService.bdGetOrdInfoPosV02(reqBody, posTypeCode);
                    	if(getOrdInfo != null){
                            resBody = getOrdInfo;
                        }else{
                            resBody = new ResCommon();
                            ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                        }
                    } else {
                        resBody = new ResCommon();
                        ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                    }
                    break;
                case SERVICE.CODE.CB02._03 : //만나주문 목록 조회 (CB02_03)
                    RequestValueCheck(reqBody.getInPosShopCode(),"in_POS_SHOP_CODE"); //고객사_가맹점_코드 누락 체크

                    //만나주문 조회 프로시저 호출
                    if (version.equals("V01")) {
                    	ArrayList<GetStOrdInfo> getStOrdList = posMapperService.bdGetStOrdListV01(reqBody, posTypeCode);
                    	if(getStOrdList != null){
                            resBody = getStOrdList;
                        }else{
                            resBody = new ResCommon();
                            ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                        }
                    } else if (version.equals("V02")) {
                    	GetStOrdInfo_V02 getStOrdInfo = posMapperService.bdGetStOrdListV02(reqBody, posTypeCode);
                    	if(getStOrdInfo != null){
                            resBody = getStOrdInfo;
                        }else{
                            resBody = new ResCommon();
                            ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                        }
                    } else {
                    	resBody = new ResCommon();
                        ((ResCommon) resBody).setErrorMsg("조회된 결과가 없습니다.");
                    }
                    break;

            }

            /** 정상(처리결과) - 0000 */
            resHeader.setTraceNo(traceNo);
            resHeader.setResCode(RES.CODE.OK);
            if(StringUtils.isEmpty(resHeader.getResMsg()))
                resHeader.setResMsg(RES.MSG.OK);
            res.setHeader(resHeader);
            res.setBody(resBody);

            return res;
        }
        catch (RequestException e) {
            /** Request 파라미터 오류(필수누락 등) - 0001 */
            log.error(e.getMessage());
            resHeader.setTraceNo(traceNo);
            resHeader.setResCode(RES.CODE.REQ_ERROR);
            resHeader.setResMsg(RES.MSG.REQ_ERROR);
            resBody = new ResCommon();
            ((ResCommon) resBody).setErrorMsg(e.getMessage());
            res.setHeader(resHeader);
            res.setBody(resBody);

            return res;

        } catch (PosConException e) {
            /** 처리거절(처리 불가능 상황) - 0002 */
            log.error(e.getMessage());
            resHeader.setTraceNo(traceNo);
            resHeader.setResCode(RES.CODE.FAIL);
            resHeader.setResMsg(RES.MSG.FAIL);
            resBody = new ResCommon();
            ((ResCommon) resBody).setErrorMsg(e.getMessage());
            res.setHeader(resHeader);
            res.setBody(resBody);

            return res;

        } catch (AlreadyCompleteException e) {
            /** 기 처리상태(이미 처리된 상태) - 0003 */
            log.error(e.getMessage());
            resHeader.setTraceNo(traceNo);
            resHeader.setResCode(RES.CODE.ALREADY_COMPLETE);
            resHeader.setResMsg(RES.MSG.ALREADY_COMPLETE);
            resBody = new ResCommon();
            ((ResCommon) resBody).setErrorMsg(e.getMessage());
            res.setHeader(resHeader);
            res.setBody(resBody);

            return res;

        } catch (AuthKeyException e) {
            /** 키값인증 오류 - 0004 */
            log.error(e.getMessage());
            resHeader.setTraceNo(traceNo);
            resHeader.setResCode(RES.CODE.AUTH_ERROR);
            resHeader.setResMsg(RES.MSG.AUTH_ERROR);
            resBody = new ResCommon();
            ((ResCommon) resBody).setErrorMsg(e.getMessage());
            res.setHeader(resHeader);
            res.setBody(resBody);

            return res;

        } catch (Exception e) {
            /** 시스템 오류(정의되지 않은 오류) - 9999 */
            commonService.errorLog(TAG, e);
            resHeader.setTraceNo(traceNo);
            resHeader.setResCode(RES.CODE.SYSTEM_ERROR);
            resHeader.setResMsg(RES.MSG.SYSTEM_ERROR);
            resBody = new ResCommon();
            ((ResCommon) resBody).setErrorMsg(e.getMessage() != null ? e.getMessage() : e.toString());
            res.setHeader(resHeader);
            res.setBody(resBody);

            return res;
        }

    }


    /**
     * 요청값 누락 체크
     * @Method Name : RequestValueCheck
     * @param str
     * @param msg
     */
    private void RequestValueCheck(Object obj, String msg){

        if(obj != null){
            if(obj.getClass().getSimpleName().equals("String"))
                if(StringUtils.isEmpty(obj)) throw new RequestException("["+msg+"] 값이 누락 되었습니다.");
        }
    }


}
