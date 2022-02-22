package aacp.server.global.common.jwt;

import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidJwtTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final AccessTokenProvider accessTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        String token = accessTokenProvider.resolveToken((HttpServletRequest) request);
        try{
        if(token != null && accessTokenProvider.validateToken(token)){
            String userIdentifier = accessTokenProvider.verifyToken(token);
            Authentication authentication = accessTokenProvider.getAuthenticiation(userIdentifier);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        }catch(InvalidJwtTokenException e){
            request.setAttribute("exception", e.getErrorCode());
        }catch(Exception e){
            request.setAttribute("exception", ErrorCode.HANDLE_ACCESS_DENIED);
        }
        chain.doFilter(request, response);
    }
}
