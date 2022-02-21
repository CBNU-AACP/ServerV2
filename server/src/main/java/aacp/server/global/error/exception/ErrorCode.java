package aacp.server.global.error.exception;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)        //Jackson 라이브러리에서 제공하는 어노테이션으로 JSON 응답값의 형식을 지정할 때 사용
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // User
    EMAIL_DUPLICATION(400, "U001", "중복된 이메일입니다" ),
    ID_DUPLICATION(400, "U002", "중복된 아이디입니다"),
    USER_NOT_EXISTED(400, "U003", "존재하지 않는 유저입니다"),
    WRONG_PASSWORD(400, "U004", "비밀번호가 잘못되었습니다");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
