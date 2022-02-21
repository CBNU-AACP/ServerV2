package aacp.server.user.service;

import aacp.server.user.domain.User;
import aacp.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserSignInService {

    private final UserRepository userRepository;

    public Long of(User user) {
        new UserValidateDuplication(userRepository).of(user.getIdentifier());
        userRepository.save(user);
        return user.getId();
    }
}
