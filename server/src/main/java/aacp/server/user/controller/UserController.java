package aacp.server.user.controller;

import aacp.server.domain.User;
import aacp.server.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequestDto request){
        User user = new User(request.identifier, bCryptPasswordEncoder.encode(request.password), request.email, request.studentId, request.schoolCode, request.phoneNumber);
        userService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserLoginRequestDto request){
        try{
            String userIdentifier = userService.login(request.identifier, request.password);
            return new ResponseEntity<>(new UserResponseDto(userIdentifier), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @AllArgsConstructor
    static class UserResponseDto{
        private String identifier;
    }


}
