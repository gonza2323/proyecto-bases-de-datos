package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.LocationDetailsDTO;
import com.gpadilla.pedidosnow.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<LocationDetailsDTO> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDetailsDTO> getLocation(@PathVariable Long locationId) {
        LocationDetailsDTO result = locationService.getLocationById(locationId );
        return ResponseEntity.ok(result);
    }
}
