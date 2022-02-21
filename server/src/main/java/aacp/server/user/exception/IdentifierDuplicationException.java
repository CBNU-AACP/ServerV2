package aacp.server.user.exception;

import aacp.server.global.error.exception.BusinessException;
import aacp.server.global.error.exception.ErrorCode;

public class IdentifierDuplicationException extends BusinessException {

    public IdentifierDuplicationException(String target) {
        super(target, ErrorCode.ID_DUPLICATION);
    }

}
