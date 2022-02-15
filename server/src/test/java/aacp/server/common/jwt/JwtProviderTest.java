package aacp.server.common.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtProviderTest {

    @Autowired JwtProvider jwtProvider;

    @Test
    public void jwt_발급_검증(){
        //given
        String identifier = "test_1234";
        String jwtToken = jwtProvider.getJwtToken(identifier);

        //when
        String verifiedToken = jwtProvider.verifyJwtToken(jwtToken);

        //then
        Assertions.assertThat(verifiedToken).isEqualTo(identifier);
    }
}