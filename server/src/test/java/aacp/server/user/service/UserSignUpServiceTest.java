package aacp.server.user.service;

import aacp.server.global.common.jwt.AccessTokenProvider;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.domain.User;
import aacp.server.user.dto.UserSignUpServiceDto;
import aacp.server.user.exception.InvalidUserIdentifier;
import aacp.server.user.exception.InvalidUserPassword;
import aacp.server.user.repository.RefreshTokenRepository;
import aacp.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class UserSignUpServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserSignUpService userSignUpService;
    @Autowired
    EntityManager em;
    @Autowired
    AccessTokenProvider accessTokenProvider;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Test
    void 로그인() {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        em.flush();
        em.clear();

        //when
        UserSignUpServiceDto dto = userSignUpService.of(user.getIdentifier(), "1234");
        List<RefreshToken> findRefreshToken = refreshTokenRepository.findByUserIdentifier(user.getIdentifier());
        String accessToken = dto.getAccessRefreshTokenDto().getAccessToken();
        String refreshToken = dto.getAccessRefreshTokenDto().getRefreshToken();

        //then
        Assertions.assertThat(dto.getIdentifier()).isEqualTo(user.getIdentifier());
        accessTokenProvider.validateToken(accessToken);
        Assertions.assertThat(findRefreshToken.get(0).getRefreshToken()).isEqualTo(refreshToken);

    }

    @Test
    void 로그인_아이디_예외() throws InvalidUserIdentifier {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        Assertions.assertThatThrownBy(() -> userSignUpService.of("test0", "1234"))
                .isInstanceOf(InvalidUserIdentifier.class)
                .hasMessage("test0");

        //then
    }

    @Test
    void 로그인_비밀번호_예외() throws InvalidUserPassword {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        Assertions.assertThatThrownBy(() -> userSignUpService.of("test1", "123"))
                .isInstanceOf(InvalidUserPassword.class)
                .hasMessage("123");

        //then
    }
}