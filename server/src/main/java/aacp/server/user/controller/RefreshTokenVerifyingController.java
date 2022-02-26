package aacp.server.user.controller;

import aacp.server.global.common.jwt.AccessTokenProvider;
import aacp.server.user.service.RefreshTokenVerifyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class RefreshTokenVerifyingController {

    private final RefreshTokenVerifyingService refreshTokenVerifyingService;
    private final AccessTokenProvider accessTokenProvider;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyingToken(@RequestHeader Map<String, String> headers){
        String refreshToken = refreshTokenVerifyingService.resolveToken(headers);
        String userIdentifier = refreshTokenVerifyingService.verifyingRefreshToken(refreshToken);
        String accessToken = accessTokenProvider.createToken(userIdentifier);
        return new ResponseEntity(accessToken, HttpStatus.OK);
    }

//  서버에서는
//	-  refresh-toke 검증 후
//		- refresh-token이 만료되었음을 알리고
//		- refresh-token의 id가 db에 있는 유저와 일치한지 확인한다
//		- 문제가 없다면 새로운 access-token을 발급해준다   -> 컨트롤러에서

}
