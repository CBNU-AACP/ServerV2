package aacp.server.user.controller;

import aacp.server.user.domain.User;
import aacp.server.user.dto.UserSignInRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserSignInControllerTest {

    @Autowired
    UserSignInController userSignInController;


    @Test
    void 회원가입(){
        //given
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        UserSignInRequestDto request = new UserSignInRequestDto(user.getIdentifier(), user.getName(), user.getPassword(), user.getEmail(), user.getStudentId(), user.getPhoneNumber());

        //when
        ResponseEntity<?> response = userSignInController.signIn(request);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}