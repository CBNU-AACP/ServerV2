package aacp.server.global.common.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public class AccessTokenProvider extends JwtProvider{

    public AccessTokenProvider(UserDetailsService userDetailsService) {
        super("access_secret_key", 172800000L , userDetailsService);
    }
}
