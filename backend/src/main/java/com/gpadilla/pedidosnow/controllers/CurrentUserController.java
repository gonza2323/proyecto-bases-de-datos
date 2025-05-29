package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.LocationDetailsDTO;
import com.gpadilla.pedidosnow.dtos.UserDetailsDTO;
import com.gpadilla.pedidosnow.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/me")
public class CurrentUserController {
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDetailsResponse> getMyDetails(@AuthenticationPrincipal Jwt jwt) {

        UserDetailsDTO userDetailsDTO = userService.getUserByAuth0Id(jwt.getSubject());

        UserDetailsResponse response = new UserDetailsResponse(
                userDetailsDTO.getEmail(),
                userDetailsDTO.getName(),
                userDetailsDTO.getLogoImgUrl()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations")
    public ResponseEntity<?> getMyLocations(@AuthenticationPrincipal Jwt jwt) {
        List<LocationDetailsDTO> locations = userService.getUserLocations(jwt.getSubject());
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(0);
    }

    public record UserDetailsResponse(String email, String name, String logoUrl) { }
    public record UserLocationsResposne(List<LocationDetailsDTO> locations) {};
}
