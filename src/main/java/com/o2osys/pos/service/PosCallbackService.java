package com.o2osys.pos.service;

import com.o2osys.pos.packet.callback.ReqPosMessage;
import com.o2osys.pos.packet.callback.ResPosMessage;

/**
   @FileName  : PosService.java
   @Description : POS 요청 처리 서비스
   @author      : KMS
   @since       : 2017. 9. 1.
   @version     : 1.0
  
   @개정이력
   
   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 1.     KMS            최초생성
 
 */
public interface PosCallbackService {

    
    /**
     * POS 요청 처리 서비스
     * @Method Name : getResPosMessage
     * @param reqPosMessage
     * @return
     */
    public ResPosMessage getResPosMessage(ReqPosMessage req) throws Exception;
    
}
