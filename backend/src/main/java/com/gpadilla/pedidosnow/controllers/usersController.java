package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.UserCreationDetails;
import com.gpadilla.pedidosnow.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class usersController {

    private final UsersService usersService;

    @GetMapping("/signup-completion")
    public ResponseEntity<?> getSignupCompletionStatus(@AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getSubject();
        boolean userHasCompletedSignup = usersService.checkUserCompletedSignUp(subject);
        var getSignupCompletionStatusResponse = new SignUpCompletionStatus(userHasCompletedSignup);
        return ResponseEntity.ok(getSignupCompletionStatusResponse);
    }

    @PostMapping()
    public ResponseEntity<?> registerUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDetails userDetails) {
        UserCreationDetails userCreationDetails = UserCreationDetails.builder()
                .email(userDetails.email)
                .name(userDetails.restaurantName)
                .legalAddress(userDetails.legalAddress)
                .auth0Id(jwt.getSubject())
                .build();
        usersService.createUser(userCreationDetails);
        return ResponseEntity.ok(userDetails);
    }

    public record SignUpCompletionStatus(boolean isCompleted) {}
    public record UserDetails(String email, String restaurantName, String legalAddress) {}
}
