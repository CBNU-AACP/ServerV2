package aacp.server.user.service;

import aacp.server.user.domain.User;
import aacp.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long register(User user){
        if(!validateDuplicateUser(user.getIdentifier())) throw new IllegalStateException("이미 존재하는 회원입니다");
        else{
            userRepository.save(user);
            return user.getId();
        }
    }

    public boolean validateDuplicateUser(String userInfo){
        return (userInfo.contains("@")) ?
                userRepository.findByEmail(userInfo).isEmpty()
                : userRepository.findByIdentifier(userInfo).isEmpty();
    }

    public String login(String identifier, String password){
        List<User> userList = userRepository.findByIdentifier(identifier);
        if(!userList.isEmpty()){
            if(!bCryptPasswordEncoder.matches(password, userList.get(0).getPassword())) throw new IllegalStateException("비밀번호가 일치하지 않습니다");
            else return identifier;
        }
        else throw new IllegalStateException("존재하지 않는 회원입니다");
    }

}
