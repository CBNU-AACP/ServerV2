package aacp.server.user.controller;

import aacp.server.global.common.jwt.JwtProvider;
import aacp.server.user.domain.User;
import aacp.server.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController

public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequestDto request){
        User user = new User(request.identifier, request.name, bCryptPasswordEncoder.encode(request.password), request.email, request.studentId, request.schoolCode, request.phoneNumber);
        userService.register(user);

        return ResponseEntity.ok()
                .body("register done");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequestDto request){
        try{
            String userIdentifier = userService.login(request.identifier,request.password);
            String jwtToken = new JwtProvider(userDetailsService).getJwtToken(request.identifier);
            System.out.println(userIdentifier + " " + jwtToken);
            return ResponseEntity.ok()
                    .body(new UserResponseDto(userIdentifier, jwtToken));
        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/check/{value}")
    public ResponseEntity<?> validateDuplicateUser(@PathVariable("value") String identifier){
        if(userService.validateDuplicateUser(identifier))   return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    static class UserRegisterRequestDto{
        public String name;
        private String identifier;
        private String password;
        private String email;
        private String studentId;
        private String schoolCode;
        private String phoneNumber;
    }

    @Data
    @AllArgsConstructor
    static class UserLoginRequestDto{
        private String identifier;
        private String password;
    }

    @Data
    @AllArgsConstructor
    static class UserResponseDto{
        private final String identifier;
        private final String jwtToken;
    }
}
