package aacp.server.global.common.jwt;

import aacp.server.global.error.exception.ErrorCode;
import aacp.server.global.error.exception.InvalidJwtTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public abstract class JwtProvider {

    private final String jwtSecretKey;
    private final Long jwtExpirationTime;
    private final UserDetailsService userDetailsService;

    public JwtProvider(String jwtSecretKey, Long jwtExpirationTime, UserDetailsService userDetailsService) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpirationTime = jwtExpirationTime;
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String identifier){
        return JWT.create()
                .withSubject("AACP")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .withClaim("id", identifier)
                .sign(Algorithm.HMAC256(jwtSecretKey));
    }

    public String verifyToken(String token){
        return JWT.require(Algorithm.HMAC256(jwtSecretKey)).build()
                .verify(token).getClaim("id").asString();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("x-access-token");
    }

    public Authentication getAuthenticiation(String userIdentifier) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userIdentifier);
        return new UsernamePasswordAuthenticationToken(userIdentifier, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) throws JWTVerificationException {
        try{
            JWT.require(Algorithm.HMAC256(jwtSecretKey)).build().verify(token).getClaim("id");
            return true;
        }
        catch(TokenExpiredException e){
            throw new InvalidJwtTokenException(ErrorCode.TOKEN_EXPIRED);
        }
        catch(SignatureVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_SIGNATURE_TOKEN);
        }
        catch(AlgorithmMismatchException e){
            throw new InvalidJwtTokenException(ErrorCode.MISMATCH_ALGORITHM);
        }
        catch(JWTVerificationException e){
            throw new InvalidJwtTokenException(ErrorCode.INVALID_TOKEN);
        }
    }
}
