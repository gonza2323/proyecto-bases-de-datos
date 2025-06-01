package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.CreateUserRequestDTO;
import com.gpadilla.pedidosnow.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup-completion")
    public ResponseEntity<?> getSignupCompletionStatus(@AuthenticationPrincipal Jwt jwt) {
        String subject = jwt.getSubject();
        boolean userHasCompletedSignup = userService.checkUserCompletedSignUp(subject);
        var getSignupCompletionStatusResponse = new SignUpCompletionStatusResponse(userHasCompletedSignup);
        return ResponseEntity.ok(getSignupCompletionStatusResponse);
    }

    @PostMapping()
    public ResponseEntity<?> registerUser(@AuthenticationPrincipal Jwt jwt, @RequestBody UserDetails userDetails) {
        CreateUserRequestDTO createUserRequestDTO = CreateUserRequestDTO.builder()
                .email(userDetails.email)
                .name(userDetails.restaurantName)
                .legalAddress(userDetails.legalAddress)
                .auth0Id(jwt.getSubject())
                .build();
        userService.createUser(createUserRequestDTO);
        return ResponseEntity.ok(userDetails);
    }

    public record SignUpCompletionStatusResponse(boolean isCompleted) {}
    public record UserDetails(String email, String restaurantName, String legalAddress) {}
}
