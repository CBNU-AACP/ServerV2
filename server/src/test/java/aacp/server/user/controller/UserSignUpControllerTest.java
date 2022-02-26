package aacp.server.user.controller;

import aacp.server.global.common.jwt.AccessTokenProvider;
import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.user.domain.User;
import aacp.server.user.dto.UserSignUpRequestDto;
import aacp.server.user.dto.UserSignUpServiceDto;
import aacp.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserSignUpControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSignUpController userSignUpController;
    @Autowired
    private AccessTokenProvider accessTokenProvider;
    @Autowired
    private RefreshTokenProvider refreshTokenProvider;

    @Test
    void 로그인(){
        User user = new User("test1","hihi", "1234", "test1@test.com","201323","23232","01000000000");
        userRepository.save(user);

        //when
        UserSignUpRequestDto dto = new UserSignUpRequestDto(user.getIdentifier(),"1234");
        ResponseEntity<UserSignUpServiceDto> responseEntity = (ResponseEntity<UserSignUpServiceDto>) userSignUpController.signUp(dto);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        accessTokenProvider.validateToken(responseEntity.getBody().getAccessRefreshTokenDto().getAccessToken());
        refreshTokenProvider.validateToken(responseEntity.getBody().getAccessRefreshTokenDto().getRefreshToken());
        Assertions.assertThat(responseEntity.getBody().getIdentifier()).isEqualTo(user.getIdentifier());
    }
}