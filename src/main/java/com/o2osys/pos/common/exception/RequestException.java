package com.o2osys.pos.common.exception;

/**
   @FileName  : RequestException.java
   @Description : 포스 연동 요청값 예외처리
   @author      : KMS
   @since       : 2017. 7. 23.
   @version     : 1.0
  
   @개정이력
   
   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 23.     KMS            최초생성
 
 */
public class RequestException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = -5816649379960963625L;

    public RequestException() {
        super("RequestException");
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(Throwable cause) {
        super(cause);
    }

    public RequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
