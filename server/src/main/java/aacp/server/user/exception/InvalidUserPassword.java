package aacp.server.user.exception;

import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidValueException;

public class InvalidUserPassword extends InvalidValueException {
    public InvalidUserPassword(String target) {
        super(target, ErrorCode.WRONG_PASSWORD);
    }
}
