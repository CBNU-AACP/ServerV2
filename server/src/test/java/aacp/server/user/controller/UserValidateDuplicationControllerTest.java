package aacp.server.user.controller;

import aacp.server.user.domain.User;
import aacp.server.user.exception.EmailDuplicationException;
import aacp.server.user.exception.IdentifierDuplicationException;
import aacp.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserValidateDuplicationControllerTest {

    @Autowired
    UserValidateDuplicationController userValidateDuplicationController;

    @Autowired
    UserRepository userRepository;

    @Test
    void 중복검사_이메일(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        //when
        String userInfo = "test@test.com";
        ResponseEntity<?> responseEntity = userValidateDuplicationController.checkDuplication(userInfo);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 중복검사_아이디(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        //when
        String userInfo = "test";
        ResponseEntity<?> responseEntity = userValidateDuplicationController.checkDuplication(userInfo);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void 중복검사_이메일_예외(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        //when
        String userInfo = "test1@test.com";

        //then
        Assertions.assertThatThrownBy(() -> userValidateDuplicationController.checkDuplication(userInfo))
                .isInstanceOf(EmailDuplicationException.class)
                .hasMessage(user.getEmail());}

    @Test
    void 중복검사_아이디_예외(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        //when
        String userInfo = "test1";

        //then
        Assertions.assertThatThrownBy(() -> userValidateDuplicationController.checkDuplication(userInfo))
                .isInstanceOf(IdentifierDuplicationException.class)
                .hasMessage(user.getIdentifier());}
}