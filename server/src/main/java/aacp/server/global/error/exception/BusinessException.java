package aacp.server.global.error.exception;

/**
 * RuntimeException : Java 가상 머신의 정상 작동 중에 던질 수 있는 예외의 수퍼 클래스.
 * BusinessException : RuntimeException을 상속받고 앞으로 정의할 세부 예외들의 수퍼 클래스.
 */

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
