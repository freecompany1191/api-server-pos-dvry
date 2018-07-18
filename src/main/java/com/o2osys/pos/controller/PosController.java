package com.o2osys.pos.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2osys.pos.common.constants.Const.SERVICE;
import com.o2osys.pos.common.service.CommonService;
import com.o2osys.pos.common.util.PosUtils;
import com.o2osys.pos.common.util.logUtil;
import com.o2osys.pos.packet.callback.ReqPosMessage;
import com.o2osys.pos.packet.callback.ReqPosMessage.ReqPosMsgBody;
import com.o2osys.pos.packet.callback.ReqPosMessage.ReqPosMsgHeader;
import com.o2osys.pos.packet.callback.ResPosMessage;
import com.o2osys.pos.service.PosCallbackService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/v1/pos")
public class PosController {

    // 로그
    private final Logger log = LoggerFactory.getLogger(PosController.class);
    private final String TAG = PosController.class.getSimpleName();

    private ObjectMapper mObjectMapper = new ObjectMapper();

    /** 공통서비스 */
    @Autowired
    private CommonService commonService;

    @Autowired
    PosCallbackService posService;

    @ApiOperation(value = "POS 주문연동 서비스", notes = "POS 주문연동 서비스", response = ResPosMessage.class)
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResPosMessage callPos(
            //@RequestHeader(value = Define.Param.X_AUTH_TOKEN) String token,
            @RequestBody ReqPosMessage reqMessage
            //@PathVariable(value = "SERVICE_CODE") String serviceCode
            )
                    throws Exception {
        ResPosMessage resMessage = new ResPosMessage();
        ReqPosMsgHeader reqHeader =  reqMessage.getHeader();
        ReqPosMsgBody reqBody = reqMessage.getBody();

        String serviceCd = reqHeader.getServiceCode();
        String posTypeCode = reqHeader.getPosTypeCode();

        //서비스 코드를 코드와 버전으로 분리하여 담는다
        Map<String, Object> serviceMap = PosUtils.getVersion(serviceCd);
        String serviceCode = (String) serviceMap.get("SERVICE_CODE");

        String ActType = "";
        //배달요청 주문 전송 (CB02_01) 일 경우 전문 구분에 따른 정보표시
        if(SERVICE.CODE.CB02._01.equals(serviceCode))
            ActType = reqBody.getInActType()+" : "+PosUtils.CodeToName("CB02_01",reqBody.getInActType());

        try {
            long startTime = logUtil.startlog(log);

            String reqMsgJson = mObjectMapper.writeValueAsString(reqMessage);
            log.info("[POS] CALLBACK REQ JSON :: ["+PosUtils.CodeToName("POS_TYPE_CODE",posTypeCode)+"] "
                    + "["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCode)+") "
                    + ActType +"] : "+reqMsgJson);

            resMessage = posService.getResPosMessage(reqMessage);

            logUtil.endlog(log, startTime);

            String resMsgJson = mObjectMapper.writeValueAsString(resMessage);
            log.info("[POS] CALLBACK RES JSON :: ["+PosUtils.CodeToName("POS_TYPE_CODE",posTypeCode)+"] "
                    + "["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCode)+") "
                    + ActType +"] : "+resMsgJson);

            return resMessage;

        } catch (Exception e) {
            commonService.errorLog(TAG, e);

            String resMsgJson = mObjectMapper.writeValueAsString(resMessage);
            log.info("[POS] CALLBACK ERROR RES JSON :: ["+PosUtils.CodeToName("POS_TYPE_CODE",posTypeCode)+"] "
                    + "["+serviceCd+"("+PosUtils.CodeToName("SERVICE_CODE",serviceCode)+") "
                    + ActType +"] : "+resMsgJson);

            return resMessage;
        }
    }
}
