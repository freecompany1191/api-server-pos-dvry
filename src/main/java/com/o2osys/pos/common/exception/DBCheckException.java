package com.o2osys.pos.common.exception;

/**
   @FileName  : PosDBCheckException.java
   @Description : DB 점검 시간 예외처리
   @author      : KMS
   @since       : 2017. 9. 4.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 4.     KMS            최초생성

 */
public class DBCheckException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = 5890461590517745070L;

    public DBCheckException() {
        super("PosDBCheckException");
    }

    public DBCheckException(String message) {
        super(message);
    }

    public DBCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBCheckException(Throwable cause) {
        super(cause);
    }

    public DBCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
