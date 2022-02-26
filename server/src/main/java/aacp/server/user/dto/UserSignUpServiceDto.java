package aacp.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpServiceDto {
    private String identifier;
    private AccessRefreshTokenDto accessRefreshTokenDto;
}
