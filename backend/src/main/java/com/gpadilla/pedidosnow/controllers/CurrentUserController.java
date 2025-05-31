package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.CreateLocationRequestDTO;
import com.gpadilla.pedidosnow.dtos.GetLocationDetailsDTO;
import com.gpadilla.pedidosnow.dtos.LocationSummaryDTO;
import com.gpadilla.pedidosnow.dtos.UserDetailsDTO;
import com.gpadilla.pedidosnow.services.LocationService;
import com.gpadilla.pedidosnow.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/me")
public class CurrentUserController {
    private final LocationService locationService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDetailsResponse> getMyDetails(@AuthenticationPrincipal Jwt jwt) {

        UserDetailsDTO userDetailsDTO = userService.getUserDetailsByAuth0Id(jwt.getSubject());

        UserDetailsResponse response = new UserDetailsResponse(
                userDetailsDTO.getEmail(),
                userDetailsDTO.getName(),
                userDetailsDTO.getLogoImgUrl()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<?> getLocation(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId) {
        GetLocationDetailsDTO location = locationService.getLocationDetailsById(locationId, jwt.getSubject());
        return ResponseEntity.ok(location);
    }

    @GetMapping("/locations")
    public ResponseEntity<?> getLocations(@AuthenticationPrincipal Jwt jwt) {
        List<LocationSummaryDTO> locations = userService.getUserLocations(jwt.getSubject());
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateLocationRequestDTO createLocationRequestDTO) {
        locationService.createLocation(jwt.getSubject(), createLocationRequestDTO);
        return ResponseEntity.ok(0);
    }

    @PutMapping("/locations/{locationId}")
    public ResponseEntity<?> updateLocation(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId, @RequestBody CreateLocationRequestDTO updateLocationRequestDTO) {
        locationService.updateLocation(jwt.getSubject(), locationId, updateLocationRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<?> deleteLocation(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId) {
        locationService.deleteLocation(jwt.getSubject(), locationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/locations/{locationId}/menu")
    public ResponseEntity<?> getLocationMenuItems(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(0);
    }

    @PostMapping("/locations/{locationId}/menu")
    public ResponseEntity<?> addLocationMenuItem(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(0);
    }

    @PatchMapping("/locations/{locationId}/menu/{itemId}")
    public ResponseEntity<?> updateLocationMenuItem(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(0);
    }

    @DeleteMapping("/locations/{locationId}/menu/{itemId}")
    public ResponseEntity<?> deleteLocationMenuItem(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(0);
    }

    public record UserDetailsResponse(String email, String name, String logoUrl) { }
}
