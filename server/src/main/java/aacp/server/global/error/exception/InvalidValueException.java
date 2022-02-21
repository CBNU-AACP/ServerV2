package aacp.server.global.error.exception;

public class InvalidValueException extends BusinessException{

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
}
