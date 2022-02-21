package aacp.server.user.service;

import aacp.server.user.exception.EmailDuplicationException;
import aacp.server.user.exception.IdentifierDuplicationException;
import aacp.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserValidateDuplication {
    private final UserRepository userRepository;

    public void of(String userInfo){
        if(userInfo.contains("@")) validateEmail(userInfo);
        else validateIdentifier(userInfo);
    }

    private void validateEmail(String email){
        if(!userRepository.findByEmail(email).isEmpty()) throw new EmailDuplicationException(email);
    }

    private void validateIdentifier(String identifier){
        if(!userRepository.findByIdentifier(identifier).isEmpty()) throw new IdentifierDuplicationException(identifier);
    }

}
