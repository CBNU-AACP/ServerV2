package aacp.server.user.exception;

import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidValueException;

public class EmailDuplicationException extends InvalidValueException {

    public EmailDuplicationException(String target){
        super(target, ErrorCode.EMAIL_DUPLICATION);
    }
}
