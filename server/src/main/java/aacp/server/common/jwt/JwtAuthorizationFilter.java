package aacp.server.common.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    // BasicAuthenticationFilter -> 시큐리티 filter 중에 하나로 권한이나 인증이 필요한 특정 주소를 요청했을때 이 필터를 탄다

    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        if(token != null && jwtProvider.validateToken(token)){
            String userIdentifier = jwtProvider.verifyJwtToken(token);
            Authentication authentication = jwtProvider.getAuthenticiation(userIdentifier);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
