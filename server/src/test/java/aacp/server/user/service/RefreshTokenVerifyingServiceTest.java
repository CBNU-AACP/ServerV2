package aacp.server.user.service;

import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.domain.User;
import aacp.server.user.repository.RefreshTokenRepository;
import aacp.server.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class RefreshTokenVerifyingServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    RefreshTokenVerifyingService refreshTokenVerifyingService;
    @Autowired
    RefreshTokenProvider refreshTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Test
    void verifyingRefreshToken() {

        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        String token = refreshTokenProvider.createToken(user.getIdentifier());
        RefreshToken refreshToken = new RefreshToken(token);
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);

        em.flush();
        em.clear();

        //when
        refreshTokenVerifyingService.verifyingRefreshToken(refreshToken);

        //then

    }
}