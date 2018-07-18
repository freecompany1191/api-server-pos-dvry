package com.o2osys.pos.common.location.exception;

/**
   @FileName  : LocationRestConnectException.java
   @Description : LOCATION REST 서비스 예외처리
   @author      : KMS
   @since       : 2017. 8. 28.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 8. 28.     KMS            최초생성

 */
public class LocationRestConnectException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = 974718217733386545L;

    public LocationRestConnectException() {
        super("LocationRestConnectException");
    }

    public LocationRestConnectException(String message) {
        super(message);
    }

    public LocationRestConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationRestConnectException(Throwable cause) {
        super(cause);
    }

    public LocationRestConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
