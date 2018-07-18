package com.o2osys.pos.common.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.o2osys.pos.common.util.CommonUtils;
import com.o2osys.pos.mapper.LogMapper;

@Service
public class LogService {
    // 로그
    private final Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private final String TAG = LogService.class.getSimpleName();

    @Autowired
    private LogMapper mLogMapper;

    /**
     * 에러로그
     *
     * @param logType
     * @param logObject
     * @param logMsg
     * @throws JsonProcessingException
     */
    @Async
    public void error(String logType, String logObject, String logMsg) throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("in_LOG_TYPE", logType);
        map.put("in_LOG_OBJECT", logObject);
        map.put("in_LOG_MSG", logMsg);

        LOGGER.error("[ERROR LOG INSERT(SP_SY_PUT_LOG_ERROR)] "+CommonUtils.jsonStringFromObject(map));

        try {
            mLogMapper.spSyPutLogError(map);
        } catch (Exception e) {
            LOGGER.error(TAG, e);
        }
    }

    /**
     * 서버 JSON 요청로그 입력
     *
     * @param logType
     * @param reqNum
     * @param logMsg
     */
    @Async
    public void tmpSvr(String logType, String reqNum, String logMsg, String userId) throws Exception{

        HashMap<String, Object> map = new HashMap<String, Object>();

        byte[] msgByte = logMsg.getBytes("UTF-8");

        LOGGER.info("msgByte = " + msgByte.length);
        //4000 바이트 이상이면 4000 바이트 까지 잘라서 저장
        if(msgByte.length > 4000){
            LOGGER.info("4000바이트 이상 원본 logMsg = " + logMsg);

            byte[] tempByte = new byte[4000];
            System.arraycopy(msgByte, 0, tempByte, 0, 4000);
            logMsg = new String(tempByte, "UTF-8");
        }

        LOGGER.info("logMsg = " + logMsg);

        map.put("in_LOG_TYPE", logType);
        map.put("in_REQ_NUM", reqNum);
        map.put("in_USER_ID", userId );
        map.put("in_DEVICE_ID", null);
        map.put("in_LOG_MSG", logMsg);
        map.put("in_APP_VER", null);


        LOGGER.info(CommonUtils.jsonStringFromObject(map));

        try {
            mLogMapper.spSyPutLogTmpSvr(map);
        } catch (Exception e) {
            LOGGER.error(TAG, e);
        }

    }
}
