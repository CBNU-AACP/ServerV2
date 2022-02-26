package aacp.server.user.dto;

import lombok.Data;

@Data
public class UserSignUpRequestDto {

    private String identifier;
    private String password;

    public UserSignUpRequestDto(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
