package com.o2osys.pos.common.exception;

/**
   @FileName  : PosConException.java
   @Description : 포스 연동 예외처리 [실패처리]
   @author      : KMS
   @since       : 2017. 9. 4.
   @version     : 1.0
  
   @개정이력
   
   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2017. 9. 4.     KMS            최초생성
 
 */
public class PosConException extends RuntimeException {

    /** long */
    private static final long serialVersionUID = 5890461590517745070L;

    public PosConException() {
        super("PosConException");
    }

    public PosConException(String message) {
        super(message);
    }

    public PosConException(String message, Throwable cause) {
        super(message, cause);
    }

    public PosConException(Throwable cause) {
        super(cause);
    }

    public PosConException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
