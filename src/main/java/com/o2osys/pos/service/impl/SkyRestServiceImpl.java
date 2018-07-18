package com.o2osys.pos.service.impl;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.o2osys.pos.common.constants.Const;
import com.o2osys.pos.common.constants.Define;
import com.o2osys.pos.common.exception.AuthException;
import com.o2osys.pos.common.exception.AuthRestConnectException;
import com.o2osys.pos.common.exception.RequestException;
import com.o2osys.pos.common.exception.RestConnectException;
import com.o2osys.pos.common.service.CommonService;
import com.o2osys.pos.common.service.LogService;
import com.o2osys.pos.common.util.CommonUtils;
import com.o2osys.pos.common.util.PosUtils;
import com.o2osys.pos.entity.rest.ConInfo;
import com.o2osys.pos.entity.rest.ConStatusInfo;
import com.o2osys.pos.packet.sky.rest.InAddGoodsData;
import com.o2osys.pos.packet.sky.rest.InGoodsData;
import com.o2osys.pos.packet.sky.rest.ReqSkyHeader;
import com.o2osys.pos.packet.sky.rest.ReqSkySD0101;
import com.o2osys.pos.packet.sky.rest.ReqSkySD0101.ReqSkySD0101Body;
import com.o2osys.pos.packet.sky.rest.ReqSkySD0201;
import com.o2osys.pos.packet.sky.rest.ReqSkySD0201.ReqSkySD0201Body;
import com.o2osys.pos.packet.sky.rest.ResSkyCommon;
import com.o2osys.pos.packet.sky.rest.ResSkyHeader;

/**
   @FileName  : SkyRestServiceImpl.java
   @Description : SKY POS REST 서비스 구현체
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.   KMS            최초생성

 */
@Service("SkyRestService")
public class SkyRestServiceImpl {
    // 로그
    private final Logger log = LoggerFactory.getLogger(SkyRestServiceImpl.class);
    private final String TAG = SkyRestServiceImpl.class.getSimpleName();

    @Autowired
    LogService logService;

    /** 공통서비스 */
    @Autowired
    CommonService commonService;

    @Autowired
    MessageSource messageSource;

    String POS_TYPE_NM = PosUtils.CodeToName("POS_TYPE_CODE",Const.POS_TYPE_CODE.SKY);

    //스카이포스 API KEY
    @Value("${sky.rest.apikey}")
    String apiKey;

    //스카이포스 SD0101 VERSION
    @Value("${sky.rest.SD0101.version}")
    String SD0101_version;

    //스카이포스 SD0201 VERSION
    @Value("${sky.rest.SD0201.version}")
    String SD0201_version;

    //스카이포스 TOKEN ID AND PASSWORD
    //@Value("${sky.token.idandpw}")
    //String idAndPw;

    //스카이포스 TOKEN URL
    //@Value("${sky.token.rest.url}")
    //String restTokenUrl;

    //스카이포스 TEST URL
    //@Value("${sky.rest.test.url}")
    //String restTestUrl;

    //스카이포스 만나주문 상태 전송(SD01_01) REST URL
    //@Value("${sky.rest.SD0101.url}")
    //String rest_SD0101_Url;

    //스카이포스 만나주문 전송(SD02_01) REST URL
    //@Value("${sky.rest.SD0201.url}")
    //String rest_SD0201_Url;


    /**
     * 만나주문 상태(1: 배차, 2: 출발, 3: 완료, 4: 취소, 5: 배차취소) 전송 (SD01_01)
     * @Method Name : RestSkySd0101
     * @param con
     * @return
     * @throws Exception
     */
    public ResSkyCommon RestSkySD0101(ConStatusInfo conStatus) throws Exception{

        String serviceCd = Const.REST.SKY.SERVICE.CODE.SD01._01;
        String reqService = serviceCd+"_"+SD0101_version;

        ResSkyCommon res = new ResSkyCommon();
        try {
            ReqSkySD0101Body reqBody = new ReqSkySD0101Body(conStatus);
            res = RestTransfer(serviceCd, reqService, reqBody, null, conStatus);
            return res;
        } catch (Exception e) {
            commonService.errorLog(TAG, e);
            logService.error(Define.Log.TYPE, TAG, e.getMessage());
            return res;
        }
    }


    /**
     * 만나주문 전송(SD02_01)
     * @Method Name : RestSkySd0201
     * @param version
     * @param con
     * @return
     * @throws Exception
     */
    public ResSkyCommon RestSkySD0201(ConInfo con) throws Exception {

        String serviceCd = Const.REST.SKY.SERVICE.CODE.SD02._01;
        String reqService = serviceCd+"_"+SD0201_version;

        ResSkyCommon res = new ResSkyCommon();
        try {
            ReqSkySD0201Body reqBody = new ReqSkySD0201Body(con);
            res = RestTransfer(serviceCd, reqService, reqBody, con, null);
            return res;
        } catch (Exception e) {
            commonService.errorLog(TAG, e);
            logService.error(Define.Log.TYPE, TAG, e.getMessage());
            return res;
        }
    }


    /**
     * 만나주문 전송(SD02_01) TEST
     * @Method Name : RestSkySd0201Test
     * @param version
     * @param con
     * @return
     * @throws Exception
     */
    public ResSkyCommon RestSkySD0201Test() throws Exception {

        ResSkyCommon res = new ResSkyCommon();
        String serviceCd = Const.REST.SKY.SERVICE.CODE.SD02._01;
        String reqService = serviceCd+"_"+SD0201_version;

        try {

            InAddGoodsData inAddGoodsData = new InAddGoodsData();
            inAddGoodsData.setInPosAddGoodsCode("0102676376");
            inAddGoodsData.setInAddGoodsNo("1");
            inAddGoodsData.setInAddGoodsName("테스트 배민2");
            inAddGoodsData.setInAddGoodsCnt("1");
            inAddGoodsData.setInAddGoodsPrice("2000");

            ArrayList<InAddGoodsData> inAddGoodsDataArr = new ArrayList<InAddGoodsData>();

            InGoodsData inGoodsData = new InGoodsData();
            inGoodsData.setInPosGoodsCode("0601100054");
            inGoodsData.setInOrdGoodsNo("");
            inGoodsData.setInGoodsName("M001");
            inGoodsData.setInOrdCnt("1");
            inGoodsData.setInOrdPrice("1000");
            inGoodsData.setInOption1Name("");
            inGoodsData.setInOption1Price("");
            inGoodsData.setInOption2Name("");
            inGoodsData.setInOption2Price("");
            inGoodsData.setInAddGoodsData(inAddGoodsDataArr);

            ArrayList<InGoodsData> InGoodsDataArr = new ArrayList<InGoodsData>();
            InGoodsDataArr.add(inGoodsData);

            ReqSkySD0201Body reqBody = new ReqSkySD0201Body();
            reqBody.setInPosShopCode("BAEMIN01");
            reqBody.setInOrdNo("170801000042");
            reqBody.setInOrdCuTel("01011112222");
            reqBody.setInOrdCuTel2("01011112222");
            reqBody.setInTakeoutYn("N");
            reqBody.setInTakeoutDate("");
            reqBody.setInReadyTime("15");
            reqBody.setInEaAddr1("서울");
            reqBody.setInEaAddr2("구로구");
            reqBody.setInEaAddr3("개봉동");
            reqBody.setInEaAddrJb("서울 구로구 개봉동 478");
            reqBody.setInEaAddrSt("서울 구로구 개봉로3길 87");
            reqBody.setInEaAddrEtc("개봉한진아파트 000-000");
            reqBody.setInEaLatY("37.48467909491101");
            reqBody.setInEaLngX("126.85194169123484");
            reqBody.setInOrdAmt("31500");
            reqBody.setInPayAmt("31500");
            reqBody.setInChargeType("1");
            reqBody.setInPayType("2");
            reqBody.setInGoodsNames("테스트");
            reqBody.setInGoodsData(InGoodsDataArr);

            res = RestTransfer(serviceCd, reqService, reqBody, null, null);

            return res;
        } catch (Exception e) {
            commonService.errorLog(TAG, e);
            return res;
        }

    }

    /**
     * SKY POS REST 요청 서비스
     * @Method Name : RestTransfer
     * @param version
     * @param serviceCd
     * @param reqBody
     * @param con
     * @return
     * @throws Exception
     */
    private ResSkyCommon RestTransfer(String serviceCd, String reqService, Object reqBody, ConInfo con, ConStatusInfo conStatus) throws Exception {

        String posShopCode = null;
        String ordNo = null;
        String errTitle = null;

        switch(serviceCd){
            case Const.REST.SKY.SERVICE.CODE.SD01._01:
                if(conStatus != null){
                    posShopCode = conStatus.getPosShopCode();
                    ordNo = conStatus.getOrdNo();
                }else errTitle = "ConStatusInfo";
                break;

            case Const.REST.SKY.SERVICE.CODE.SD02._01:
                if(con != null){
                    posShopCode = con.getPosShopCode();
                    ordNo = con.getOrdNo();
                }else errTitle = "ConInfo";
                break;
        }

        if(conStatus == null && con == null)
            throw new RequestException("["+POS_TYPE_NM+"] REST FAIL :: ["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")]"
                    + errTitle+" IS NULL");

        HttpStatus resStatusCode = null;

        ResponseEntity<ResSkyCommon> resMsg = null;
        ResSkyCommon res = new ResSkyCommon();
        ResSkyHeader resHeader = new ResSkyHeader();

        String _traceNo = ""; //전문추적번호 - YYYYMMDDHH24MISS + Random4자리
        String _resCode = ""; //처리결과코드
        String _resMsg = "";  //처리결과메세지

        try {

            //요청 정보를 전송하고 응답 메세지를 받아온다
            resMsg = getResMsg(serviceCd, reqService, reqBody, con, conStatus);
            resStatusCode = resMsg.getStatusCode();

            //전송 성공일 때만 셋팅
            if (resStatusCode == HttpStatus.OK) {

                res.setHeader(resMsg.getBody().getHeader());
                res.setBody(resMsg.getBody().getBody());

                if(res.getHeader() != null && res.getBody() !=null){
                    _traceNo = res.getHeader().getTraceNo();
                    _resCode = res.getHeader().getResCode();
                    _resMsg = res.getHeader().getResMsg();

                }else{

                    throw new RestConnectException("["+POS_TYPE_NM+"] REST_RESPONSE_ERROR ::  SERVICE = "+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")"
                            + ", POS_SHOP_CODE = "+posShopCode+", ORD_NO = "+ordNo+", ERROR_MSG = " +messageSource.getMessage("error.notResponsData",null,Locale.KOREA));

                }

            }else { //(resStatusCode != null && resStatusCode != HttpStatus.OK){

                throw new RestConnectException("["+POS_TYPE_NM+"] REST_CONNECT_ERROR ::  SERVICE = "+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")"
                        + ", POS_SHOP_CODE = "+posShopCode+", ORD_NO = "+ordNo+", ERROR_MSG = " +messageSource.getMessage("error.notConnect",null,Locale.KOREA));

            }

            //응답 JSON 로그
            log.info("["+POS_TYPE_NM+"] REST RES JSON :: ["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")] : "+CommonUtils.jsonStringFromObject(res));

            //요청이 실패 하면 에러로그에 등록시킨다
            if(!_resCode.equals(Const.RES.CODE.OK) && !_resCode.equals(Const.RES.CODE.ALREADY_COMPLETE)) {
                throw new RequestException("["+POS_TYPE_NM+"] REST FAIL :: ["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")]"
                        + ", TRACE_NO = " + _traceNo+", POS_SHOP_CODE = "+posShopCode+", ORD_NO = "+ordNo+""
                        + ", RES_CODE = "+_resCode +"["+PosUtils.CodeToName("RES_CODE",_resCode) +"], RES_MSG = "+_resMsg );
            }

            //이미 처리된 건에 대한 응답처리
            if(_resCode.equals(Const.RES.CODE.ALREADY_COMPLETE)){
                log.info("["+POS_TYPE_NM+"] REST ALREADY COMPLETE :: ["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")]"
                        + ", TRACE_NO = " + _traceNo+", POS_SHOP_CODE = "+posShopCode+", ORD_NO = "+ordNo+""
                        + ", RES_CODE = "+_resCode +"["+PosUtils.CodeToName("RES_CODE",_resCode) +"], RES_MSG = "+_resMsg );
            }

        } catch (AuthException | AuthRestConnectException e) {
            //commonService.errorLog(TAG, e);
            resHeader.setResCode(Const.RES.CODE.AUTH_ERROR);
            resHeader.setResMsg(Const.RES.MSG.AUTH_ERROR);
            res.setHeader(resHeader);
            String errorMsg = "["+POS_TYPE_NM+"] REST GET TOKEN FAIL :: ["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")]"
                    + ", POS_SHOP_CODE = "+posShopCode+", ORD_NO = "+ordNo+""
                    + ", "+e.getMessage();
            log.error(errorMsg);
            logService.error(Define.Log.TYPE, TAG, errorMsg);
            return res;

        } catch (RequestException e) {
            //commonService.errorLog(TAG, e);
            resHeader.setResCode(Const.RES.CODE.REQ_ERROR);
            resHeader.setResMsg(Const.RES.MSG.REQ_ERROR);
            res.setHeader(resHeader);
            log.error(e.getMessage());
            logService.error(Define.Log.TYPE, TAG, e.getMessage());
            return res;

        } catch (RestConnectException | ResourceAccessException | ConnectException e) {
            //commonService.errorLog(TAG, e);
            resHeader.setResCode(Const.RES.CODE.NOT_CONNECT);
            resHeader.setResMsg(Const.RES.MSG.NOT_CONNECT);
            res.setHeader(resHeader);
            log.error(e.getMessage());
            logService.error(Define.Log.TYPE, TAG, e.getMessage());
            return res;

        } catch (Exception e) {
            commonService.errorLog(TAG, e);
            resHeader.setResCode(Const.RES.CODE.SYSTEM_ERROR);
            res.setHeader(resHeader);
            logService.error(Define.Log.TYPE, TAG, "["+POS_TYPE_NM+"] REST_ERROR :: SERVICE = "+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")"
                    + ", POS_SHOP_CODE = "+posShopCode+", ORD_NO = "+ordNo+", ERROR_MSG = " + e.getMessage());
            return res;
        }

        return res;
    }

    /**
     * 요청 정보를 전송하고 응답 메세지를 받아온다
     * @Method Name : getResMsg
     * @param version
     * @param serviceCd
     * @param reqBody
     * @param con
     * @return
     * @throws Exception
     */
    private ResponseEntity<ResSkyCommon> getResMsg(String serviceCd, String reqService, Object reqBody, ConInfo con, ConStatusInfo conStatus) throws Exception{

        RestTemplate restTemplate = new RestTemplate();

        //API KEY 셋팅
        String key = null;
        //주문 전송 URL 셋팅
        String url = null;
        //토큰 셋팅
        String token = null;

        switch(serviceCd){
            case Const.REST.SKY.SERVICE.CODE.SD01._01:
                key = conStatus.getConData2();
                url = conStatus.getConUrl();
                //주문상태 전송 URL 셋팅
                if(StringUtils.isEmpty(url)) //없으면 config에 설정된 값 셋팅
                    throw new RequestException("NOT_REST_SKY_SD0101_URL");

                token = getToken(conStatus.getConData3(), conStatus.getTokenAuthInfo());
                break;

            case Const.REST.SKY.SERVICE.CODE.SD02._01:
                key = con.getConData2();
                url = con.getConUrl();
                //주문 전송 URL 셋팅
                if(StringUtils.isEmpty(url)) //없으면 config에 설정된 값 셋팅
                    throw new RequestException("NOT_REST_SKY_SD0201_URL");

                token = getToken(con.getConData3(), con.getTokenAuthInfo());
                break;
        }

        //API KEY 셋팅
        if(StringUtils.isEmpty(key)){ //없으면 config에 설정된 값 셋팅
            key = apiKey;
        }


        ReqSkyHeader reqHeader = new ReqSkyHeader(key, CommonUtils.getTraceNo(4) , reqService, Const.POS_TYPE_CODE.SKY);

        Object reqMsg = null;
        HttpEntity<Object> entity = null;
        ResponseEntity<ResSkyCommon> resMsg = null;

        switch(serviceCd) {
            case Const.REST.SKY.SERVICE.CODE.SD01._01 : //만나주문 상태(1: 배차, 2: 출발, 3: 완료, 4: 취소, 5: 배차취소) 전송(SD01_01_V01)

                reqMsg = new ReqSkySD0101(reqHeader, (ReqSkySD0101Body) reqBody);
                entity = new HttpEntity<Object>(reqMsg, PosUtils.getHeader(token));

                break;

            case Const.REST.SKY.SERVICE.CODE.SD02._01 : //만나주문 전송(SD02_01_V01)

                reqMsg = new ReqSkySD0201(reqHeader, (ReqSkySD0201Body) reqBody);
                entity = new HttpEntity<Object>(reqMsg, PosUtils.getHeader(token));

                break;

        }

        resMsg = restTemplate.exchange(url, HttpMethod.POST, entity, ResSkyCommon.class);

        //요청 JSON 로그
        log.info("["+POS_TYPE_NM+"] REST REQ JSON :: ["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCd)+")] : "+CommonUtils.jsonStringFromObject(reqMsg));

        return resMsg;

    }

    /**
     * 토큰을 가져온다
     * @Method Name : getToken
     * @param url
     * @param tokenAuthInfo
     * @return
     * @throws Exception
     */
    private String getToken(String url, String tokenAuthInfo) throws Exception{
    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>(0)");
    	log.info("url:" + url + " tokenAuthInfo:" + tokenAuthInfo);

        RestTemplate restTemplate = new RestTemplate();

        String token = "";

        //인증 서버 URL 셋팅
        if(StringUtils.isEmpty(url)) //없으면 config에 설정된 값 셋팅
            throw new AuthException("NOT TOKEN_AUTH_URL");

        if(StringUtils.isEmpty(tokenAuthInfo))
            throw new AuthException("NOT TOKEN_AUTH_INFO");

        // 인증은  Http Basic Authorization
        String tokenAuthInfoByte = org.apache.commons.codec.binary.StringUtils.newStringUtf8(Base64.encodeBase64(tokenAuthInfo.getBytes()));

        //헤더값 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "Basic "+tokenAuthInfoByte);

        //전송 객체 셋팅
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //요청 JSON 로그
        log.info("["+POS_TYPE_NM+"] REST TOKEN REQ JSON :: "+CommonUtils.jsonStringFromObject(entity.getHeaders()));

        //POST 요청 및 응답은 HashMap 형태로 받아온다
        ResponseEntity<HashMap> resMsg = restTemplate.exchange(url, HttpMethod.POST, entity, HashMap.class);

        log.info("["+POS_TYPE_NM+"] REST TOKEN RES JSON :: "+CommonUtils.jsonStringFromObject(resMsg.getBody()));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        //전송상태
        HttpStatus resStatusCode = resMsg.getStatusCode();

        //전송 성공일 때만 셋팅
        if (resStatusCode == HttpStatus.OK) {

            //맵에 응답 바디를 담는다
            Map<String, Object> resultMap = resMsg.getBody();
            Iterator<String> iterator = resultMap.keySet().iterator();
            String keyName = null;

            //응답 값 확인
            while (iterator.hasNext()) {
                keyName = iterator.next();

                log.debug("["+POS_TYPE_NM+"] GET TOKEN :: KEY : "+ keyName+", VALUE : "+resultMap.get(keyName));
                /*
                if("token".equals(keyName)){
                    token = String.valueOf(resultMap.get(keyName));
                }
                 */
            }

            //에러가 발생했을 경우 Exception 처리
            int err_cd = (int) resultMap.get("err_cd");
            if(err_cd != 0){
                throw new AuthException("err_cd : "+err_cd+", err_msg : "+resultMap.get("err_msg"));
            }

            //성공이면 토큰값을 담아 넘긴다
            token = String.valueOf(resultMap.get("token"));

            //토큰 서버 접속 실패일때 Exception 처리
        }else { //(resStatusCode != null && resStatusCode != HttpStatus.OK){

            throw new AuthRestConnectException("ERROR_MSG = " +messageSource.getMessage("error.notConnect",null,Locale.KOREA));

        }

        return token;

    }
}
