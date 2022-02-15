package aacp.server.global.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final String jwtSecretKey = "accp-secret";
    private final Long jwtExpirationTime = 1200000L;
    private final UserDetailsService userDetailsService;

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

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("x-access-token");
    }

    public boolean validateToken(String jwtToken){
        try{
            Date expiresAt = JWT.require(Algorithm.HMAC256(jwtSecretKey)).build()
                    .verify(jwtToken).getExpiresAt();
            return !expiresAt.before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    public Authentication getAuthenticiation(String userIdentifier) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userIdentifier);
        return new UsernamePasswordAuthenticationToken(userIdentifier, "", userDetails.getAuthorities());
    }
}
