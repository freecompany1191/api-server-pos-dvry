package com.o2osys.pos.common.exception;

/**
   @FileName  : AuthException.java
   @Description : 연동 키인증 예외처리
   @author      : KMS
   @since       : 2017. 7. 23.
   @version     : 1.0
  
   @개정이력
   
   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 23.     KMS            최초생성
 
 */
public class AuthKeyException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = -5816649379960963625L;

    public AuthKeyException() {
        super("AuthException");
    }

    public AuthKeyException(String message) {
        super(message);
    }

    public AuthKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthKeyException(Throwable cause) {
        super(cause);
    }

    public AuthKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
