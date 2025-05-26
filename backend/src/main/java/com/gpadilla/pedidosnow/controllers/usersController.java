package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.repositories.RestaurantRepository;
import com.gpadilla.pedidosnow.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class usersController {

    private final UsersService usersService;

    @GetMapping("/signup-completion")
    public ResponseEntity<SignupStatus> getSignupCompletionStatus(@AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getSubject();
        SignupStatus signupStatus = new SignupStatus(usersService.checkUserCompletedSignUp(subject));
        return ResponseEntity.ok(signupStatus);
    }

    @PostMapping()
    public int registerUser(@AuthenticationPrincipal Jwt jwt) {

        return 0;
    }

    public record SignupStatus(boolean isCompleted) {}
}
