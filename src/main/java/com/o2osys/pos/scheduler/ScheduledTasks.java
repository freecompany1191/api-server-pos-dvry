package com.o2osys.pos.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.o2osys.pos.common.service.CommonService;
import com.o2osys.pos.service.PosScheduledService;

/**
   @FileName  : ScheduledTasks.java
   @Description : POS 연동 스케쥴러
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.     KMS            최초생성

 */
@Component
public class ScheduledTasks {
    private final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private final String TAG = ScheduledTasks.class.getSimpleName();

    @Autowired
    private PosScheduledService posScheduledService;

    /** 공통서비스 */
    @Autowired
    private CommonService commonService;

    /** 전체 스케쥴러 동작여부 */
    @Value("${all.scheduler.use}")
    boolean schedulerUseYN;

    /**
     * POS 연동 주문 전송 REST 스케쥴러 (3초 마다 수행)
     * @Method Name : RestTransfer
     */
    @Scheduled(fixedRate = 1000L * 3)
    public void RestOrderTransfer() {

        //스케쥴러 사용모드가 true 이면 스케쥴러 수행
        if(schedulerUseYN){
            //log.debug("[REST InputOrder Transefer] START =====================================");

            try {

                int listSize = posScheduledService.restInputOrder();
                log.debug("[REST GET_CON_LIST] SIZE = "+listSize);

            } catch (Exception e) {
                commonService.errorLog(TAG, e);
            }

            //log.debug("[REST InputOrder Transefer] END =====================================");
        }

    }


    /**
     * POS 연동 주문상태 전송 REST 스케쥴러 (3초 마다 수행)
     * @Method Name : RestOrderStatusTransfer
     */
    @Scheduled(fixedRate = 1000L * 3)
    public void RestOrderStatusTransfer() {

        //스케쥴러 사용모드가 true 이면 스케쥴러 수행
        if(schedulerUseYN){
            //log.debug("[REST UpdateOrder Transefer] START =====================================");

            try {

                int listSize = posScheduledService.restUpdateOrder();
                log.debug("[REST GET_CON_STATUS_LIST] SIZE = "+listSize);

            } catch (Exception e) {
                commonService.errorLog(TAG, e);
            }

            //log.debug("[REST UpdateOrder Transefer] END =====================================");
        }

    }

}
