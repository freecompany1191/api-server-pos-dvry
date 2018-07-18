package com.o2osys.pos.service;

/**
   @FileName  : PosScheduledService.java
   @Description : POS 스케쥴링 서비스
   @author      : KMS
   @since       : 2017. 10. 20.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 10. 20.   KMS            최초생성

 */
public interface PosScheduledService {

    /**
     * 만나주문 전송(SD02_01)
     * @Method Name : restInputOrder
     * @return
     * @throws Exception
     */
    public int restInputOrder() throws Exception;

    /**
     * 만나주문 상태 전송(SD01_01)
     * @Method Name : restUpdateOrder
     * @return
     * @throws Exception
     */
    public int restUpdateOrder() throws Exception;

}