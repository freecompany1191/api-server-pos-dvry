package com.o2osys.pos.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.o2osys.pos.common.constants.Const;
import com.o2osys.pos.common.exception.DBCheckException;
import com.o2osys.pos.common.exception.PosConException;
import com.o2osys.pos.common.util.CommonUtils;
import com.o2osys.pos.common.util.PosUtils;
import com.o2osys.pos.entity.rest.ConInfo;
import com.o2osys.pos.entity.rest.ConStatusInfo;
import com.o2osys.pos.packet.sky.rest.ResSkyCommon;
import com.o2osys.pos.service.PosScheduledService;

/**
   @FileName  : PosScheduledServiceImpl.java
   @Description : POS 스케쥴링 서비스 구현체
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.   KMS            최초생성

 */
@Service("PosScheduledService")
public class PosScheduledServiceImpl implements PosScheduledService {
    // 로그
    private final Logger log = LoggerFactory.getLogger(PosScheduledServiceImpl.class);
    private final String TAG = PosScheduledServiceImpl.class.getSimpleName();

    @Autowired
    SkyRestServiceImpl skyRestService;

    @Autowired
    PosScheduledMapperServiceImpl posScheduledMapperService;

    /** 전체 스케쥴러 동작여부 */
    @Value("${all.scheduler.use}")
    boolean schedulerUseYN;

    /** 스카이 포스 스케쥴러 동작여부 */
    @Value("${sky.scheduler.use}")
    boolean skyUseYN;

    /**
     * 만나주문 전송(SD02_01)
     * @Method Name : restInputOrder
     * @return
     * @throws Exception
     */
    @Override
    public int restInputOrder() throws Exception {
        //배달대행 연동 대상 목록 리스트
        ArrayList<ConInfo> conList = null;

        try {
            conList = posScheduledMapperService.bdGetConList();

            if(conList == null){
                log.error("[REST GET_CON_LIST] 04시50분 - 05시00분 일일정산 처리 시간 :: "+conList,conList);
                return 0;
            }

            // 일일정산 처리
        } catch (DBCheckException e) {
            log.error(e.getMessage());
            return 0;
        }

        int listSize = conList.size();
        if(listSize == 0) return 0;


        try {
            int i=0;
            //POS 연동 대상 목록 리스트 REST 처리
            for(ConInfo con : conList){
                if(con != null){
                    switch(con.getPosTypeCd()){

                        case Const.POS_TYPE_CODE.SKY : //SKY포스 타입 처리

                            if(skyUseYN){//SKY포스 스케쥴러 사용여부가 true이면

                                String POS_TYPE_NM = PosUtils.CodeToName("POS_TYPE_CODE",Const.POS_TYPE_CODE.SKY);
                                log.info("["+POS_TYPE_NM+" NEW ORDER REST] =========================================");
                                log.info("["+POS_TYPE_NM+"] REST CON_INFO JSON ["+i+"] : "+CommonUtils.jsonStringFromObject(con));

                                //SKY포스 만나주문 전송(SD02_01) 서비스 호출
                                ResSkyCommon resSkyCommon = skyRestService.RestSkySD0201(con);

                                //REST 성공 시 처리
                                if(resSkyCommon.getHeader().getResCode().equals(Const.RES.CODE.OK)){
                                    posScheduledMapperService.updateConStatus(con, Const.STATUS.SUCCESS); //연동상태 전송완료처리
                                    posScheduledMapperService.updateSD0201(resSkyCommon.getBody());       //만나주문 전송(SD02_01) 리턴값 업데이트
                                }
                                //이미 처리된 상태
                                else if(resSkyCommon.getHeader().getResCode().equals(Const.RES.CODE.ALREADY_COMPLETE)){
                                    posScheduledMapperService.updateConStatus(con, Const.STATUS.SUCCESS); //연동상태 전송완료처리
                                }
                                //연동 실패처리
                                else{
                                    posScheduledMapperService.updateConStatus(con, Const.STATUS.FAIL);    //연동상태 전송실패처리
                                }

                            }

                            break;

                    }//end switch

                    i++;
                }
            }
        } catch (PosConException e) {
            log.error("PKG_SY_CON_POS.BD_GET_CON_LIST ERROR : "+e.getMessage());
        }

        return listSize;
    }

    /**
     * 만나주문 상태 전송(SD01_01)
     * @Method Name : restUpdateOrder
     * @return
     * @throws Exception
     */
    @Override
    public int restUpdateOrder() throws Exception {
        //배달대행 연동 대상 목록 리스트
        ArrayList<ConStatusInfo> conStatusList = null;

        try {
            conStatusList = posScheduledMapperService.bdGetConStatusList();

            if(conStatusList == null){
                log.error("[REST GET_CON_STATUS_LIST] 04시50분 - 05시00분 일일정산 처리 시간 :: "+conStatusList,conStatusList);
                return 0;
            }

            // 일일정산 처리
        } catch (DBCheckException e) {
            log.error(e.getMessage());
            return 0;
        }

        int listSize = conStatusList.size();
        if(listSize == 0) return 0;


        try {
            int i=0;
            //POS 연동 대상 목록 리스트 REST 처리
            for(ConStatusInfo con : conStatusList){
                if(con != null){
                    switch(con.getPosTypeCd()){

                        case Const.POS_TYPE_CODE.SKY : //SKY포스 타입 처리

                            if(skyUseYN){//SKY포스 스케쥴러 사용여부가 true이면

                                String POS_TYPE_NM = PosUtils.CodeToName("POS_TYPE_CODE",Const.POS_TYPE_CODE.SKY);
                                log.info("["+POS_TYPE_NM+" NEW ORDER STATUS REST] =========================================");
                                log.info("["+POS_TYPE_NM+"] REST CON_STATUS_INFO JSON ["+i+"] : "+CommonUtils.jsonStringFromObject(con));

                                //SKY포스 만나주문 전송(SD02_01) 서비스 호출
                                ResSkyCommon resSkyCommon = skyRestService.RestSkySD0101(con);

                                //REST 성공 시 처리
                                if(resSkyCommon.getHeader().getResCode().equals(Const.RES.CODE.OK)){
                                    posScheduledMapperService.updateConStsStatus(con, Const.STATUS.SUCCESS); //연동상태 전송완료처리
                                }
                                //이미 처리된 상태
                                else if(resSkyCommon.getHeader().getResCode().equals(Const.RES.CODE.ALREADY_COMPLETE)){
                                    posScheduledMapperService.updateConStsStatus(con, Const.STATUS.SUCCESS); //연동상태 전송완료처리
                                }
                                else{
                                    posScheduledMapperService.updateConStsStatus(con, Const.STATUS.FAIL);    //연동상태 전송실패처리
                                }

                            }

                            break;

                    }//end switch

                    i++;
                }
            }
        } catch (PosConException e) {
            log.error("PKG_SY_CON_POS.BD_GET_CON_STATUS_LIST ERROR : "+e.getMessage());
        }

        return listSize;
    }

}