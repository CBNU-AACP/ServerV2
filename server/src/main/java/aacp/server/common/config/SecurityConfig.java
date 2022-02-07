package aacp.server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity      //스프링 Security 필터가 스프링 필터 체인에 등록이 된다
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors().disable()
                .csrf().disable()
                .formLogin().disable();
                //loginProcessingUrl("/api/users/login");
                //.headers().frameOptions().disable()

        http.authorizeRequests()
                .antMatchers("/api/users/**").permitAll()
                .anyRequest().authenticated();
    }
}
