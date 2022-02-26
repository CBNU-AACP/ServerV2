package aacp.server.user.controller;

import aacp.server.user.dto.UserSignUpRequestDto;
import aacp.server.user.dto.UserSignUpServiceDto;
import aacp.server.user.service.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserSignUpRequestDto request){
        UserSignUpServiceDto dto = userSignUpService.of(request.getIdentifier(), request.getPassword());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
