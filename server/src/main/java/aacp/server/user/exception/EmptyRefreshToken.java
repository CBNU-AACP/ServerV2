package aacp.server.user.exception;

import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidValueException;

public class EmptyRefreshToken extends InvalidValueException {
    public EmptyRefreshToken(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }
}
