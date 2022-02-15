package aacp.server.global.config;

import aacp.server.global.common.jwt.JwtAuthorizationFilter;
import aacp.server.global.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity      //스프링 Security 필터가 스프링 필터 체인에 등록이 된다
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final JwtProvider jwtProvider;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();  // 인터넷 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위를 특정 웹사이트에 요청하게 만드는 공격
                                // csrf는 브라우저의 쿠키의 세션에 있는 정보로 권한을 얻는다
                                // rest-api 구조에서는 쿠키의 세션을 이용하지 않으므로 csrf를 disable해도 된다

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // security session을 사용하지 않는다
                .and()
                .addFilter(corsFilter)      //Security 필터에 cors 정책을 걸어준다
                .formLogin().disable()
                .httpBasic().disable();     // httpBasic : http header에 Id, pw를 넣어서 보내주는 방식, 보안이 약하다
                                            // 우린 authorization에 토큰을 넣을 것(httpBearer 방식)이므로 httpBasic을 false로 줄 것

        http.authorizeRequests()
                .antMatchers("/api/users/*").permitAll()       //api/users/**는 로그인, 중복 id 검사 등등 이므로 모든 권한을 가진 자들에게 공개
                .anyRequest().authenticated()                              // 그 밖에 모든 요청은 jwt를 통해서
        .and()
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

    }
}
