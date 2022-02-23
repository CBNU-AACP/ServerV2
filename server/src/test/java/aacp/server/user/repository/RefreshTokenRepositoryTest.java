package aacp.server.user.repository;

import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@SpringBootTest
@Transactional
class RefreshTokenRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired RefreshTokenRepository refreshTokenRepository;
    @Autowired
    RefreshTokenProvider refreshTokenProvider;
    @Autowired
    UserRepository userRepository;

    @Test
    void save() {
        //given
        String identifier = "test1234";
        String token = refreshTokenProvider.createToken(identifier);
        RefreshToken refreshToken = new RefreshToken(token);
        Long savedId = refreshTokenRepository.save(refreshToken);

        em.flush();
        em.clear();

        //when
        RefreshToken findToken = refreshTokenRepository.findById(savedId);

        //then
        Assertions.assertThat(findToken.getRefreshToken()).isEqualTo(token);
    }

    @Test
    void findByUserIdentifier() {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        String token = refreshTokenProvider.createToken(user.getIdentifier());
        RefreshToken refreshToken = new RefreshToken(token);
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);

        em.flush();
        em.clear();

        String inputIdentifier = user.getIdentifier();
        // String inputIdentifier = "test12;

        //when
        List<RefreshToken> findRefreshToken = refreshTokenRepository.findByUserIdentifier(inputIdentifier);

        //then
        Assertions.assertThat(user.getIdentifier()).isEqualTo(refreshToken.getUser().getIdentifier());

        Assertions.assertThat(findRefreshToken.get(0).getRefreshToken()).isEqualTo(refreshToken.getRefreshToken());
        Assertions.assertThat(findRefreshToken.get(0).getUser().getIdentifier()).isEqualTo(user.getIdentifier());
    }
}