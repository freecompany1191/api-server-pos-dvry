package com.o2osys.pos.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
   @FileName  : CommonService.java
   @Description : 공통 서비스
   @author      : KMS
   @since       : 2017. 7. 21.
   @version     : 1.0
  
   @개정이력
   
   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 21.     KMS            최초생성
 
 */
@Service
public class CommonService {
	// 로그
	private final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
	private final String TAG = CommonService.class.getSimpleName();
	
	/**
	 * 에러로그를 뿌려준다
	 * @param e
	 */
	public void errorLog(String errorTag, Exception e) {
		LOGGER.error(errorTag, e);
		LOGGER.error(e.getMessage());
		LOGGER.error(e.toString());
        StackTraceElement[] ste = e.getStackTrace();
        for(int i = 0; i < ste.length; i++)
        {
        	LOGGER.error(String.valueOf(ste[i]));
        }
	}
	
}
