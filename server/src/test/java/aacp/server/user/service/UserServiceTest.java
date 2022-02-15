package aacp.server.user.service;

import aacp.server.domain.User;
import aacp.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    void register() {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        //when
        Long savedId = userService.register(user);
        User findUser = userRepository.findById(savedId);

        //then

        Assertions.assertThat(findUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(findUser.getIdentifier()).isEqualTo(user.getIdentifier());
    }

    @Test()
    void register_exception() throws Exception {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when,then
        Assertions.assertThatThrownBy(() -> userService.register(user))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("이미 존재하는 회원입니다");

    }

    @Test
    void validateDuplicateUser(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        boolean savedState = userService.validateDuplicateUser(user.getEmail());

        //then
        Assertions.assertThat(savedState).isEqualTo(false);
    }

    @Test
    void login() {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        String savedIdentifier = userService.login(user.getIdentifier(), user.getPassword());

        //then
        Assertions.assertThat(savedIdentifier).isEqualTo(user.getIdentifier());
    }

    @Test
    void login_exception() {
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        Assertions.assertThatThrownBy(() -> userService.login(user.getIdentifier(), "1234"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("비밀번호가 일치하지 않습니다");

        //then
    }
}