package aacp.server.global.common.jwt.impl;

import aacp.server.user.domain.User;
import aacp.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsSerivceImpl implements UserDetailsService {

    /**
     * UserDetail : Spring Security에서 사용자의 정보를 담는 인터페이스.
     * UserDetailsService : Spring Security에서 사용자의 정보를 가져오는 인터페이스
     * UserDetailsService가 왜 필요할까?
     *  SpringContextHolder는 Spring Security 인증의 가장 핵심적인 모델이고 그것은
     *  SecurityContext를 포함한다. SecurityContextHolder는 누가 authenticated되었는지에 대한
     *  정보가 담기게 되고 정보가 담기면 현재 인증된 유저라는 것을 뜻함
     *  이 SpringContext는 또 Authentication으로 이루어지고 Authenticatio 안에는 principals가 있는데
     *  이 pricipals는 UserDetail의 인스턴스이다
     *  그래서 이 user가 인증된 유저인지 확인하기 위해서는 SecurityContextHolder에 정보가 담겨야 하고
     *  그 principals에 UserDetail의 인스턴스가 담겨야한다
     *
     *  아직은 권한이 구분되어 있지 않아서 사용하지 않는다
     */

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        List<User> findUserIdentifier = userRepository.findByIdentifier(userIdentifier);
        if(findUserIdentifier.isEmpty()) throw new UsernameNotFoundException(userIdentifier);
        else return new UserDetailsImpl(userIdentifier, "");
    }
}
