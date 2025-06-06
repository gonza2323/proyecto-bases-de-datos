package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.*;
import com.gpadilla.pedidosnow.repositories.RestaurantRepository;
import com.gpadilla.pedidosnow.services.LocationService;
import com.gpadilla.pedidosnow.services.MenuItemService;
import com.gpadilla.pedidosnow.services.ReportService;
import com.gpadilla.pedidosnow.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/me")
public class CurrentUserController {
    private final LocationService locationService;
    private final UserService userService;
    private final MenuItemService menuItemService;
    private final ReportService reportService;

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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/locations/{locationId}")
    public ResponseEntity<?> updateLocation(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId, @RequestBody CreateLocationRequestDTO updateLocationRequestDTO) {
        locationService.updateLocation(jwt.getSubject(), locationId, updateLocationRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<?> deleteLocation(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId) {
        locationService.deleteLocation(jwt.getSubject(), locationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/locations/{locationId}/menu")
    public ResponseEntity<?> addLocationMenuItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId, @RequestBody CreateMenuItemRequestDTO createMenuItemRequestDTO) {
        GetMenuItemDetailsDTO result = menuItemService.createMenuItem(jwt.getSubject(), locationId, createMenuItemRequestDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/menu/{itemId}")
    public ResponseEntity<?> updateLocationMenuItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemId, @RequestBody CreateMenuItemRequestDTO updateMenuItemRequestDTO) {
        GetMenuItemDetailsDTO result = menuItemService.updateMenuItem(jwt.getSubject(), itemId, updateMenuItemRequestDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/menu/{itemId}")
    public ResponseEntity<?> deleteLocationMenuItem(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemId) {
        menuItemService.deleteMenuItem(jwt.getSubject(), itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/locations/{locationId}/open")
    public ResponseEntity<?> setLocationIsOpen(@AuthenticationPrincipal Jwt jwt, @PathVariable Long locationId, @RequestBody boolean isOpen) {
        locationService.setLocationIsOpen(jwt.getSubject(), locationId, isOpen);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/menu/{itemId}/available")
    public ResponseEntity<?> setMenuItemIsAvailable(@AuthenticationPrincipal Jwt jwt, @PathVariable Long itemId, @RequestBody boolean isAvailable) {
        menuItemService.setItemIsAvailable(jwt.getSubject(), itemId, isAvailable);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/report")
    public void downloadTestFile(HttpServletResponse response, @AuthenticationPrincipal Jwt jwt) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\"");

        reportService.generateLocationReport(jwt.getSubject(), response.getOutputStream());

        response.flushBuffer();
    }

    public record UserDetailsResponse(String email, String name, String logoUrl) { }
}
