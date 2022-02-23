package aacp.server.global.common.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenProvider extends JwtProvider{

    public AccessTokenProvider(UserDetailsService userDetailsService) {
        super(userDetailsService);
        setJwtSecretKeyAndExpirationTime("access_secret_key",172800000L);
    }

}
