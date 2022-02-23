package aacp.server.user.service;

import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidValueException;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.exception.EmptyRefreshToken;
import aacp.server.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void verifyingRefreshToken(RefreshToken refreshToken){
        if(refreshToken == null) throw new EmptyRefreshToken(ErrorCode.EMPTY_REFRESH_TOKEN);
        refreshTokenProvider.validateToken(refreshToken.getRefreshToken());
        matchingWithId(refreshToken.getUser().getIdentifier());
    }

    private void matchingWithId(String userIdentifier){
        List<RefreshToken> findToken = refreshTokenRepository.findByUserIdentifier(userIdentifier);
        if(findToken.isEmpty()) throw new InvalidValueException("Refresh token이 존재하지 않습니다.",ErrorCode.ENTITY_NOT_FOUND);
    }
}
