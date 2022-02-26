package aacp.server.user.controller;

import aacp.server.user.domain.User;
import aacp.server.user.dto.UserSignInRequestDto;
import aacp.server.user.service.UserSignInService;
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
public class UserSignInController {

    private final UserSignInService userSignInService;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserSignInRequestDto request){
        User user = request.toEntity();
        userSignInService.of(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
