package aacp.server.user.service;

import aacp.server.user.domain.User;
import aacp.server.user.exception.IdentifierDuplicationException;
import aacp.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class UserSignInServiceTest {
    @Autowired UserSignInService userSignInService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    void 회원가입() {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        //when
        Long savedId = userSignInService.of(user);
        User findUser = userRepository.findById(savedId);

        //then
        Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(findUser.getIdentifier()).isEqualTo(user.getIdentifier());
    }

    @Test()
    void 중복_회원가입() throws IdentifierDuplicationException {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when,then
        Assertions.assertThatThrownBy(() -> userSignInService.of(user))
                .isInstanceOf(IdentifierDuplicationException.class)
                .hasMessage(user.getIdentifier());
    }
}