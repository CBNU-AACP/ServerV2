package aacp.server.user.dto;

import aacp.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInRequestDto {

    private String identifier;
    private String name;
    private String password;
    private String email;
    private String studentId;
    private String phoneNumber;

    public User toEntity(){
        return new User(identifier, name, password, email, studentId, null, phoneNumber);
    }
}
