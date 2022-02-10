package aacp.server.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final String jwtSecretKey = "accp-secret";
    private final Long jwtExpirationTime = 1200000L;

    public String getJwtToken(String identifier){
        return JWT.create()
                .withSubject("AACP")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .withClaim("id", identifier)
                .sign(Algorithm.HMAC256(jwtSecretKey));
    }

    public String verifyJwtToken(String token){
        return JWT.require(Algorithm.HMAC256(jwtSecretKey)).build()
                .verify(token).getClaim("id").asString();
    }
}
