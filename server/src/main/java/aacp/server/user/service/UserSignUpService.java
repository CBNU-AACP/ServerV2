package aacp.server.user.service;

import aacp.server.global.common.jwt.AccessTokenProvider;
import aacp.server.global.common.jwt.RefreshTokenProvider;
import aacp.server.user.domain.RefreshToken;
import aacp.server.user.domain.User;
import aacp.server.user.dto.AccessRefreshTokenDto;
import aacp.server.user.dto.UserSignUpServiceDto;
import aacp.server.user.exception.InvalidUserIdentifier;
import aacp.server.user.exception.InvalidUserPassword;
import aacp.server.user.repository.RefreshTokenRepository;
import aacp.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSignUpService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;

    public UserSignUpServiceDto of(String identifier, String password){
        String userPassword = verifyIdentifier(identifier);
        verifyPassword(password, userPassword);
        AccessRefreshTokenDto jwtTokens = issueJwtTokens(identifier);
        saveRefreshToken(jwtTokens.getRefreshToken(), identifier);
        return new UserSignUpServiceDto(identifier,jwtTokens);
    }

    private String verifyIdentifier(String identifier){
        List<User> findUser = userRepository.findByIdentifier(identifier);      //조회가 되지 않습니다
        if(findUser.isEmpty()) throw new InvalidUserIdentifier(identifier);
        else return findUser.get(0).getPassword();
    }

    private void verifyPassword(String comparingPassword, String userPassword){
        boolean matches = bCryptPasswordEncoder.matches(comparingPassword, userPassword);
        if(!matches) throw new InvalidUserPassword(comparingPassword);
    }

    private AccessRefreshTokenDto issueJwtTokens(String userIdentifier){
        String accessToken = new AccessTokenProvider(userDetailsService).createToken(userIdentifier);
        String refreshToken = new RefreshTokenProvider(userDetailsService).createToken(userIdentifier);
        return new AccessRefreshTokenDto(accessToken, refreshToken);
    }

    private void saveRefreshToken(String refreshToken, String userIdentifier){
        RefreshToken savedToken = new RefreshToken(refreshToken);
        List<User> findUser = userRepository.findByIdentifier(userIdentifier);
        savedToken.setUser(findUser.get(0));
        refreshTokenRepository.save(savedToken);
    }
}
