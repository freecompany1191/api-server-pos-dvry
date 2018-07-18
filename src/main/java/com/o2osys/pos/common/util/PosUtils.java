package com.o2osys.pos.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.o2osys.pos.common.constants.Const;
import com.o2osys.pos.common.exception.RequestException;

/**
   @FileName  : PosUtils.java
   @Description : POS 연동 유틸
   @author      : KMS
   @since       : 2017. 9. 4.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 4.     KMS            최초생성

 */
public class PosUtils {


    /**
     * 서비스 코드와 버전을 분리해서 가져온다
     * @Method Name : getVersion
     * @param serviceCode
     * @return
     */
    public static Map<String, Object> getVersion(String serviceCode){

        if(StringUtils.isEmpty(serviceCode)){
            throw new RequestException("["+serviceCode+"] 서비스 코드가 없습니다.");
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();

        //버전을 담을 변수
        String version = null;

        String[] strArr = serviceCode.split("_");

        if(strArr.length == 3){
            version = strArr[2]; //버전만 뽑아서 담는다
        }else{
            throw new RequestException("["+serviceCode+"] 잘못된 서비스 코드 입니다.");
        }

        //버전 제거
        serviceCode = serviceCode.replaceAll("_"+strArr[2], "");

        resultMap.put("SERVICE_CODE", serviceCode);
        resultMap.put("VERSION", version);

        return resultMap;

    }

    /**
     * 스카이 포스 전문 헤더 설정
     *
     * @Method Name : getHeader
     * @return
     */
    public static HttpHeaders getHeader(String token){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "Bearer "+token);

        return headers;
    }


    /**
     * POS 코드를 이름으로
     * @Method Name : CodeToName
     * @param type
     * @param code
     * @return
     */
    public static String CodeToName(String type, String code){

        String name = "";
        switch(type){

            /** 만나주문 등록 요청 */
            case "POS_TYPE_CODE" :

                switch(code){

                    case Const.POS_TYPE_CODE.UNITAS :
                        name = "UNITAS";
                        break;
                    case Const.POS_TYPE_CODE.SPC :
                        name = "SPC";
                        break;
                    case Const.POS_TYPE_CODE.SKY :
                        name = "SKY";
                        break;
                    case Const.POS_TYPE_CODE.EASY:
                        name = "EASY";
                        break;

                }
                break;

                /** 서비스 정보 */
            case "SERVICE_CODE" :

                switch(code){
                    case Const.SERVICE.CODE.CB01._01 :
                        name = Const.SERVICE.NAME.CB01._01;
                        break;
                    case Const.SERVICE.CODE.CB01._02 :
                        name = Const.SERVICE.NAME.CB01._02;
                        break;
                    case Const.SERVICE.CODE.CB01._03 :
                        name = Const.SERVICE.NAME.CB01._03;
                        break;

                    case Const.SERVICE.CODE.CB02._01 :
                        name = Const.SERVICE.NAME.CB02._01;
                        break;
                    case Const.SERVICE.CODE.CB02._02 :
                        name = Const.SERVICE.NAME.CB02._02;
                        break;
                    case Const.SERVICE.CODE.CB02._03 :
                        name = Const.SERVICE.NAME.CB02._03;
                        break;

                    case Const.REST.SKY.SERVICE.CODE.SD01._01 :
                        name = Const.REST.SKY.SERVICE.NAME.SD01._01;
                        break;
                    case Const.REST.SKY.SERVICE.CODE.SD02._01 :
                        name = Const.REST.SKY.SERVICE.NAME.SD02._01;
                        break;
                }

                break;

                /** 만나주문 등록 요청 */
            case "CB02_01" :

                switch(code){

                    case Const.SERVICE.CODE.CB02._01_CODE.REQUEST :
                        name = Const.SERVICE.NAME.CB02._01_NAME.REQUEST;
                        break;

                    case Const.SERVICE.CODE.CB02._01_CODE.MODIFY :
                        name = Const.SERVICE.NAME.CB02._01_NAME.MODIFY;
                        break;

                    case Const.SERVICE.CODE.CB02._01_CODE.CANCEL :
                        name = Const.SERVICE.NAME.CB02._01_NAME.CANCEL;
                        break;

                }
                break;

                /** 응답 정보 */
            case "RES_CODE" :

                switch(code){
                    case Const.RES.CODE.OK:
                        name = Const.RES.MSG.OK;
                        break;
                    case Const.RES.CODE.REQ_ERROR:
                        name = Const.RES.MSG.REQ_ERROR;
                        break;
                    case Const.RES.CODE.FAIL:
                        name = Const.RES.MSG.FAIL;
                        break;
                    case Const.RES.CODE.ALREADY_COMPLETE:
                        name = Const.RES.MSG.ALREADY_COMPLETE;
                        break;
                    case Const.RES.CODE.AUTH_ERROR:
                        name = Const.RES.MSG.AUTH_ERROR;
                        break;
                    case Const.RES.CODE.BAD_ORDER:
                        name = Const.RES.MSG.BAD_ORDER;
                        break;
                    case Const.RES.CODE.SYSTEM_ERROR:
                        name = Const.RES.MSG.SYSTEM_ERROR;
                        break;
                }
                break;

        }
        return name;
    }

}
