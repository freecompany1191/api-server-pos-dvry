package com.o2osys.pos.common.exception;

/**
   @FileName  : AuthRestConnectException.java
   @Description : 인증 REST 서비스 예외처리
   @author      : KMS
   @since       : 2017. 7. 23.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 23.     KMS            최초생성

 */
public class AuthRestConnectException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = 946419619199522005L;

    public AuthRestConnectException() {
        super("AuthRestConnectException");
    }

    public AuthRestConnectException(String message) {
        super(message);
    }

    public AuthRestConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthRestConnectException(Throwable cause) {
        super(cause);
    }

    public AuthRestConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
