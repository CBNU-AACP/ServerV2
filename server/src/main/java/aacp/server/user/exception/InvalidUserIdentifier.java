package aacp.server.user.exception;

import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidValueException;

public class InvalidUserIdentifier extends InvalidValueException {
    public InvalidUserIdentifier(String target) {
        super(target, ErrorCode.USER_NOT_EXISTED);
    }
}
