package aacp.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessRefreshTokenDto {
    private String accessToken;
    private String refreshToken;
}
