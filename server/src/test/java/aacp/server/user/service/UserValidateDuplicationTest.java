package aacp.server.user.service;

import aacp.server.user.domain.User;
import aacp.server.user.exception.EmailDuplicationException;
import aacp.server.user.exception.IdentifierDuplicationException;
import aacp.server.user.exception.InvalidUserIdentifier;
import aacp.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserValidateDuplicationTest {

    @Autowired UserValidateDuplication userValidateDuplication;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    void 아이디_중복(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when

        //then
        Assertions.assertThatThrownBy(() -> userValidateDuplication.of(user.getIdentifier()))
                .isInstanceOf(IdentifierDuplicationException.class)
                .hasMessage(user.getIdentifier());
    }

    @Test
    void 이메일_중복(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when

        //then
        Assertions.assertThatThrownBy(() -> userValidateDuplication.of(user.getEmail()))
                .isInstanceOf(EmailDuplicationException.class)
                .hasMessage(user.getEmail());
    }

    @Test
    void 아이디_중복X(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        String fakeId = "test0";

        //then
        userValidateDuplication.of(fakeId);
    }

    @Test
    void 이메일_중복X(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        String fakeEmail = "test0@test.com";

        //then
        userValidateDuplication.of(fakeEmail);
    }
}