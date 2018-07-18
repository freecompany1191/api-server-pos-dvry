package com.o2osys.pos.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2osys.pos.common.constants.Define;

/**
   @FileName  : CommonUtil.java
   @Description : 공통유틸
   @author      : KMS
   @since       : 2017. 7. 21.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 21.     KMS            최초생성

 */
public class CommonUtils {

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
     * 날짜 형식 변경
     * yyyyMMddHHmmss -> yyyy-MM-dd HH:mm:ss
     * @Method Name : strToDate
     * @param strDate
     * @return
     */
    public static String strToDate(String strDate) {

        if(StringUtils.isEmpty(strDate)){
            return null;
        }

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.KOREA);
        LocalDateTime toDate = LocalDateTime.parse(strDate,pattern);

        pattern = DateTimeFormatter.ofPattern("yyyy.MM.dd HH;mm;ss", Locale.KOREA);
        String formatStr = pattern.format(toDate);

        return formatStr;
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
     * 현재시간 (YYYYMMDDHH24MISS)
     *
     * @Method Name : getNowTime
     * @return
     */
    public static String getNowTime(){

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        LocalDateTime now = LocalDateTime.now();
        String nowTime = now.format(format);

        return nowTime;
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
     * 연동 공통 전문 헤더 설정
     *
     * @Method Name : getHeader
     * @return
     */
    public static String CodeToName(String type, String code) {

        String name = "";

        if(StringUtils.isEmpty(code)){
            return name;
        }

        switch(type){

            /** 결제 구분(1. 도착지 결제, 2. 선결제) */
            case "CHARGE_TYPE" :

                switch(code){
                    case Define.ChargeType.TYPE_1 :
                        name = "도착지 결제";
                        break;
                    case Define.ChargeType.TYPE_2 :
                        name = "선결제";
                        break;
                }
                break;

                /** 지불 구분(1001. 현금, 1002. 카드, 1003. 만나페이) */
            case "PAY_TYPE_CD" :

                switch(code){
                    case Define.PayTypeCd.CASH :
                        name = "현금";
                        break;
                    case Define.PayTypeCd.CARD :
                        name = "카드";
                        break;
                    case Define.PayTypeCd.MANNA_PAY :
                        name = "만나페이";
                        break;
                }
                break;

        }
        return name;
    }

}
