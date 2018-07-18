package com.o2osys.pos.common.location.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2osys.pos.common.location.constants.LocaionConst;

public class LocationCommonUtils {

    /**
     * Object -> JSON 형식으로 변환
     *
     * @Method Name : jsonStringFromObject
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String jsonStringFromObject(Object object) throws JsonProcessingException {

        if(object == null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * 공통 전문 헤더 설정
     *
     * @Method Name : getHeader
     * @return
     */
    public static HttpHeaders getHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return headers;
    }

    /**
     * 전문추적번호 생성(YYYYMMDDHH24MISS + Random 자릿수 지정)
     *
     * @Method Name : getTraceNo
     * @return
     */
    public static String getTraceNo(int num){

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        //입력된 숫자만큼 Random 자릿수 셋팅
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<num;i++) {
            sb.append("9");
        }
        int cnt = Integer.valueOf(sb.toString());

        Random rn = new Random();
        String rnum = String.format("%0"+num+"d", rn.nextInt(cnt));

        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(format);
        String traceNo = nowTime + rnum;

        return traceNo;
    }

    /**
     * 공통 전문 헤더 설정
     * @Method Name : CodeToName
     * @param type
     * @param code
     * @return
     */
    public static String CodeToName(String type, String code){

        String name = "";
        switch(type){

            /** REST 응답 코드 */
            case "RES_CODE" :

                switch(code){
                    case LocaionConst.RES_CODE.OK :
                        name = LocaionConst.RES_CODE_NAME.OK;
                        break;
                    case LocaionConst.RES_CODE.FAIL :
                        name = LocaionConst.RES_CODE_NAME.FAIL;
                        break;
                    case LocaionConst.RES_CODE.REQ_ERROR :
                        name = LocaionConst.RES_CODE_NAME.REQ_ERROR;
                        break;
                    case LocaionConst.RES_CODE.SYSTEM_ERROR :
                        name = LocaionConst.RES_CODE_NAME.SYSTEM_ERROR;
                        break;
                }
                break;

        }
        return name;
    }

}
