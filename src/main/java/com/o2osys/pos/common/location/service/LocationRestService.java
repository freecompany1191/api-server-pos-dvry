package com.o2osys.pos.common.location.service;

import java.net.ConnectException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.o2osys.pos.common.location.constants.LocaionConst;
import com.o2osys.pos.common.location.exception.LocaionRequestException;
import com.o2osys.pos.common.location.exception.LocationRestConnectException;
import com.o2osys.pos.common.location.packet.Location;
import com.o2osys.pos.common.location.packet.ReqRestLocation;
import com.o2osys.pos.common.location.packet.ReqRestLocation.ReqBody;
import com.o2osys.pos.common.location.packet.ReqRestLocation.ReqHeader;
import com.o2osys.pos.common.location.packet.ResRestLocation;
import com.o2osys.pos.common.location.packet.ResRestLocation.ResResLocationHeader;
import com.o2osys.pos.common.service.CommonService;

/**
@FileName  : LocationRestService.java
@Description : 주소 API 요청 공통 로직
@author      : KMS
@since       : 2017. 12. 11.
@version     : 1.0

@개정이력

수정일          수정자         수정내용
-----------     ---------      -------------------------------
2017. 12. 11.    KMS            최초생성

 */
@Service("LocationRestService")
public class LocationRestService {

    // 로그
    private final Logger log = LoggerFactory.getLogger(LocationRestService.class);
    private final String TAG = LocationRestService.class.getSimpleName();

    /** 공통서비스 */
    @Autowired
    CommonService commonService;

    @Value("${location.api1.url}")
    String locationUrl_1;

    @Value("${location.api2.url}")
    String locationUrl_2;

    @Autowired
    MessageSource messageSource;

    /**
     * LOCATION REST 요청을 통해 정보 조회
     * @Method Name : getLocation
     * @param serviceCd
     * @param address
     * @return
     * @throws Exception
     */
    public Location getLocation(String serviceCd, String address) throws Exception{

        //요청 시작 로그
        log.info("=============================================================");
        log.info("[LOCATION] REST START ["+serviceCd+"] :: ADDRESS : "+address);
        Location location = RestTransfer(serviceCd, address);
        //결과 로그
        log.info("[LOCATION] REST END ["+serviceCd+"] ===================================");

        return location;
    }

    /**
     * LOCATION REST 요청 서비스
     * @Method Name : RestTransfer
     * @param version
     * @param serviceCd
     * @param address
     * @param con
     * @return
     * @throws Exception
     */
    private Location RestTransfer(String serviceCd, String address) throws Exception {

        ResRestLocation resRestLocation = new ResRestLocation();

        Location location = null; //조회된 위치정보

        String _traceNo = ""; //전문추적번호 - YYYYMMDDHH24MISS + Random3자리
        String _resCode = ""; //처리결과코드
        String _resMsg = "";  //처리결과메세지

        boolean conn = false; //접속성공여부

        String[] locationArr = new String[]{locationUrl_1, locationUrl_2};

        try {

            //연결 실패시 재시도 횟수만큼 반복 시도
            for(String locationUrl : locationArr){
                log.info("[LOCATION] REST TRY URL :: "+locationUrl);
                resRestLocation = reTryConnect(locationUrl, serviceCd, address);
                _traceNo = resRestLocation.getHeader().getTraceNo();
                _resCode = resRestLocation.getHeader().getResCode();
                _resMsg = resRestLocation.getHeader().getResMsg();
                conn = resRestLocation.getHeader().isConn();
                if(conn){
                    location = resRestLocation.getBody().getLocation();
                    break; //성공이면 루프문 탈출
                }

            }

            if(!conn){ //(resStatusCode != null && resStatusCode != HttpStatus.OK){
                throw new LocationRestConnectException("[LOCATION] REST_CONNECT_ERROR :: ERROR_MSG = " +messageSource.getMessage("error.notConnect",null,Locale.KOREA));
            }

            //응답 JSON 로그
            log.info("[LOCATION] REST RES JSON ["+serviceCd+"] :: "+LocationCommonUtils.jsonStringFromObject(resRestLocation));

            //요청이 실패 하면 에러로그에 등록시킨다
            if(!_resCode.equals(LocaionConst.RES_CODE.OK)) {
                throw new LocaionRequestException("[LOCATION] REST FAIL :: TRACE_NO = " + _traceNo+", RES_CODE = "+_resCode +"["+LocationCommonUtils.CodeToName("RES_CODE",_resCode) +"], RES_MSG = "+_resMsg );
            }

        } catch (LocaionRequestException e) {
            log.error(e.getMessage());
        } catch (LocationRestConnectException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            commonService.errorLog(TAG, e);
        }

        return location;
    }


    /**
     * 연결 재시도 로직(서버 접속이 원할하지 않을 시 3회 재시도 후 다른 API서버를 호출)
     * @Method Name : reTryConnect
     * @param locationUrl
     * @param serviceCd
     * @param address
     * @throws Exception
     */
    private ResRestLocation reTryConnect(String locationUrl, String serviceCd, String address) throws Exception{

        HttpStatus resStatusCode = null;

        ResponseEntity<ResRestLocation> resMsg = null;
        ResRestLocation resRestLocation = new ResRestLocation();
        ResResLocationHeader resHeader = new ResResLocationHeader();

        int retry = 3;    //재시도 횟수
        int delay = 5000; //재시도 딜레이 타임

        while(retry > 0){
            //요청 정보를 전송하고 응답 메세지를 받아온다
            try {
                resMsg = getResRestLocation(locationUrl, serviceCd, address);
                resStatusCode = resMsg.getStatusCode();
                log.debug("[LOCATION] REST RESPONSE HTTP STATUS :: "+resStatusCode);

                //전송 성공일 때만 셋팅
                if (resStatusCode == HttpStatus.OK) {

                    resRestLocation = resMsg.getBody();
                    resRestLocation.getHeader().setConn(true);
                    break;

                }
                //400 에러이면 재시도 하지 않음
                else if(resStatusCode == HttpStatus.BAD_REQUEST){
                    log.error("[LOCATION] REST_CONNECT_FAIL NOT RETRY");
                    retry=0;
                }
                else{
                    log.error("[LOCATION] REST_CONNECT_FAIL HTTP STATUS :: "+resStatusCode);
                    log.error("[LOCATION] REST_CONNECT_FAIL RETRY COUNT :: "+retry);
                    retry--;
                    Thread.sleep(delay);
                }

            } catch (HttpClientErrorException | HttpServerErrorException e) {
                log.error("[LOCATION] REST_CONNECT_FAIL RESPONSE HTTP STATUS :: "+e.getStatusCode()); //429
                //log.error("[LOCATION] REST_CONNECT_FAIL RESPONSE HTTP REASON PHRASE :: "+e.getStatusCode().getReasonPhrase()); //Too Many Requests
                //log.error("[LOCATION] REST_CONNECT_FAIL RESPONSE HTTP getRawStatusCode :: "+e.getRawStatusCode()); //429
                log.error("[LOCATION] REST_CONNECT_FAIL RESPONSE HTTP MSG :: "+e.getResponseBodyAsString()); //429 Too Many Requests
                //400 에러이면 재시도 하지 않음
                if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                    log.error("[LOCATION] REST_CONNECT_FAIL NOT RETRY");
                    retry=0;
                }
                else{
                    log.error("[LOCATION] REST_CONNECT_FAIL RETRY COUNT :: "+retry);
                    retry--;
                    Thread.sleep(delay);
                }
            } catch (ResourceAccessException | ConnectException e) {

                log.error("[LOCATION] REST_CONNECT_FAIL NOT RETRY :: "+e.getMessage());
                retry=0;

            } catch (Exception e) {
                log.error("[LOCATION] REST_CONNECT_FAIL Exception Error :: "+e.getMessage());
                log.error("[LOCATION] REST_CONNECT_FAIL RETRY COUNT :: "+retry);
                retry--;
                commonService.errorLog(TAG, e);
                Thread.sleep(delay);
            }

        }

        //요청이 실패 하였을 때 커넥트 실패처리
        if(resRestLocation.getHeader() == null){

            resHeader.setConn(false);
            resRestLocation.setHeader(resHeader);

        }

        return resRestLocation;
    }


    /**
     * 요청 정보를 전송하고 응답 메세지를 받아온다
     * @Method Name : getResMsg
     * @param serviceCd
     * @param reqBody
     * @return
     * @throws Exception
     */
    private ResponseEntity<ResRestLocation> getResRestLocation(String locationUrl, String serviceCd, String address) throws Exception{

        RestTemplate restTemplate = new RestTemplate();

        ReqHeader reqHeader = new ReqHeader(LocationCommonUtils.getTraceNo(3) , serviceCd);
        ReqBody reqBody = new ReqBody(address);
        ReqRestLocation reqRestLocation = new ReqRestLocation(reqHeader, reqBody);

        HttpEntity<ReqRestLocation> entity = new HttpEntity<ReqRestLocation>(reqRestLocation, LocationCommonUtils.getHeader());
        ResponseEntity<ResRestLocation> resMsg = resMsg = restTemplate.exchange(locationUrl, HttpMethod.POST, entity, ResRestLocation.class);

        //요청 JSON 로그
        log.info("[LOCATION] REST REQ JSON ["+serviceCd+"] :: "+LocationCommonUtils.jsonStringFromObject(reqRestLocation));

        return resMsg;

    }
}
