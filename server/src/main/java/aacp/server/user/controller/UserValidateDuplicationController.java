package aacp.server.user.controller;

import aacp.server.user.service.UserValidateDuplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserValidateDuplicationController {

    private final UserValidateDuplication userValidateDuplication;

    @GetMapping("/check/{userInfo}")
    public ResponseEntity<?> checkDuplication(@PathVariable String userInfo){
        userValidateDuplication.of(userInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
