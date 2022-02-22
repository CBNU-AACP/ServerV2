package aacp.server.global.common.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 *  Refresh-token model 생성 -> User와 1대1 관계의 테이블 v
 *  refresh-token 생성 코드 만들기 -> access-token의 유효기간을 2일, refresh-token 유효기간은 2주로 설정 v
 *  로그인 시에 access-token과 마찬가지로 refresh-token도 발급, refresh-token은 발급 후 DB에 저장
 *  access-token 검증 시 만료를 알리는 코드 추가         v
 *  access-token 만료 시에 refresh-token 검증하는 코드 추가
 */

@Component
public class RefreshTokenProvider extends JwtProvider{

    public RefreshTokenProvider(UserDetailsService userDetailsService) {
        super("refresh_secret_key", 1209600000L, userDetailsService);
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("x-refresh-token");
    }
}
