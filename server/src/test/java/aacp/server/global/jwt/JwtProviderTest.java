package aacp.server.global.jwt;

import aacp.server.global.common.jwt.JwtProvider;
import aacp.server.global.error.exception.InvalidJwtTokenException;
import aacp.server.user.exception.InvalidUserPassword;
import antlr.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void jwt_발급_검증(){
        //given
        String identifier = "test_1234";
        String jwtToken = jwtProvider.createToken(identifier);

        //when
        String verifiedToken = jwtProvider.verifyToken(jwtToken);

        //then
        Assertions.assertThat(verifiedToken).isEqualTo(identifier);
    }

    @Test
    public void 토큰예외_유효기간() throws InterruptedException {
        //given
        String identifier = "test_1234";
        String expiredToken = createCustomToken(identifier,0L, "accp-secret");

        //when
        Thread.sleep(1000);

        //then
        Assertions.assertThatThrownBy(() -> jwtProvider.validateToken(expiredToken))
                .isInstanceOf(InvalidJwtTokenException.class)
                .hasMessage("토큰 유효기간이 만료되었습니다");
    }

    @Test
    public void 토큰예외_시그니처() {
        //given
        String identifier = "test_1234";
        String wrongSignatureToken = createCustomToken(identifier,3000L, "secret");
        System.out.println(wrongSignatureToken);

        //when

        //then
        Assertions.assertThatThrownBy(() -> jwtProvider.validateToken(wrongSignatureToken))
                .isInstanceOf(InvalidJwtTokenException.class)
                .hasMessage("토큰 서명 정보가 올바르지 않습니다");
    }

    @Test
    public void 토큰예외_알고리즘() {
        //given
        String identifier = "test_1234";
        String wrongAlgorithmToken = createWrongAlgorithmToken(identifier,3000L, "accp-secret");

        //when

        //then
        Assertions.assertThatThrownBy(() -> jwtProvider.validateToken(wrongAlgorithmToken))
                .isInstanceOf(InvalidJwtTokenException.class)
                .hasMessage("토큰 암호화 알고리즘이 맞지 않습니다");
    }


    private String createCustomToken(String identifier, Long jwtExpirationTime, String jwtSecretKey){
        return JWT.create()
                .withSubject("AACP")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .withClaim("id", identifier)
                .sign(Algorithm.HMAC256(jwtSecretKey));
    }

    private String createWrongAlgorithmToken(String identifier, Long jwtExpirationTime, String jwtSecretKey){
        return JWT.create()
                .withSubject("AACP")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .withClaim("id", identifier)
                .sign(Algorithm.HMAC512(jwtSecretKey));
    }
}