package aacp.server.user.controller;

import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.domain.User;
import aacp.server.user.repository.RefreshTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@Transactional
class RefreshTokenVerifyingControllerTest {

    @Autowired
    RefreshTokenVerifyingController refreshTokenVerifyingController;
    @Autowired
    RefreshTokenProvider refreshTokenProvider;
    @Autowired
    EntityManager em;

    @Test
    void refreshToken_검증(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        String token = refreshTokenProvider.createToken(user.getIdentifier());
        RefreshToken refreshToken = new RefreshToken(token);
        refreshToken.setUser(user);

        em.persist(user);
        em.persist(refreshToken);

        em.flush();
        em.clear();

        Map<String, String> headers = new HashMap<>();
        headers.put("x-refresh-token",token);

        //when
        ResponseEntity<?> responseEntity = refreshTokenVerifyingController.verifyingToken(headers);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}