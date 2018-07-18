package com.o2osys.pos.common.exception;

/**
   @FileName  : AuthException.java
   @Description : 인증 에러
   @author      : KMS
   @since       : 2017. 7. 23.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 23.     KMS            최초생성

 */
public class AuthException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = -6234821622474618342L;

    public AuthException() {
        super("AuthException");
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
