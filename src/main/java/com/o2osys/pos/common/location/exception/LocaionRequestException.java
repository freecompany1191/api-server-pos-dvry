package com.o2osys.pos.common.location.exception;

/**
   @FileName  : LocaionRequestException.java
   @Description : LOCATION 요청값 예외처리
   @author      : KMS
   @since       : 2017. 7. 23.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 23.     KMS            최초생성

 */
public class LocaionRequestException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = -5816649379960963625L;

    public LocaionRequestException() {
        super("LocaionRequestException");
    }

    public LocaionRequestException(String message) {
        super(message);
    }

    public LocaionRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocaionRequestException(Throwable cause) {
        super(cause);
    }

    public LocaionRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
