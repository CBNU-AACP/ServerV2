package aacp.server.user.service;

import aacp.server.global.common.jwt.JwtProvider;
import aacp.server.user.domain.User;
import aacp.server.user.dto.UserSignUpDto;
import aacp.server.user.exception.InvalidUserIdentifier;
import aacp.server.user.exception.InvalidUserPassword;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;

    public UserSignUpDto of(String identifier, String password){
        String userPassword = verifyIdentifier(identifier);
        verifyPassword(password, userPassword);
        return new UserSignUpDto(identifier,getJwtToken(identifier));
    }

    private String verifyIdentifier(String identifier){
        List<User> findUser = userRepository.findByIdentifier(identifier);
        if(findUser.isEmpty()) throw new InvalidUserIdentifier(identifier);
        else return findUser.get(0).getPassword();
    }

    private void verifyPassword(String comparingPassword, String userPassword){
        boolean matches = bCryptPasswordEncoder.matches(comparingPassword, userPassword);
        if(!matches) throw new InvalidUserPassword(comparingPassword);
    }

    private String getJwtToken(String userIdentifier){
        return new JwtProvider(userDetailsService).getJwtToken(userIdentifier);
    }
}
