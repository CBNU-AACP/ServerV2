package aacp.server.user.service;

import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidValueException;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.domain.User;
import aacp.server.user.exception.EmptyRefreshToken;
import aacp.server.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshTokenVerifyingService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenProvider refreshTokenProvider;

//  서버에서는
//	-  refresh-toke 검증 후
//		- refresh-token이 만료되었음을 알리고
//		- refresh-token의 id가 db에 있는 유저와 일치한지 확인한다
//		- 문제가 없다면 새로운 access-token을 발급해준다   -> 컨트롤러에서

    public String resolveToken(Map<String, String> httpHeaders){
        String refreshToken = httpHeaders.get("x-refresh-token");
        return refreshToken;
    }

    public String verifyingRefreshToken(String refreshToken){
        if(refreshToken == null) throw new EmptyRefreshToken(ErrorCode.EMPTY_REFRESH_TOKEN);
        refreshTokenProvider.validateToken(refreshToken);
        List<User> findUser = refreshTokenRepository.findUserByToken(refreshToken);
        if(findUser.isEmpty()) throw new InvalidValueException("user가 존재하지 않습니다",ErrorCode.ENTITY_NOT_FOUND);
        matchingWithId(findUser.get(0).getIdentifier());
        return findUser.get(0).getIdentifier();
    }

    private void matchingWithId(String userIdentifier){
        List<RefreshToken> findToken = refreshTokenRepository.findByUserIdentifier(userIdentifier);
        if(findToken.isEmpty()) throw new InvalidValueException("Refresh token이 존재하지 않습니다.",ErrorCode.ENTITY_NOT_FOUND);
    }
}
