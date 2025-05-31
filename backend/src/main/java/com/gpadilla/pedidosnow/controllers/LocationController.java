package com.gpadilla.pedidosnow.controllers;

import com.gpadilla.pedidosnow.dtos.LocationSummaryDTO;
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
    public List<LocationSummaryDTO> getLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationSummaryDTO> getLocation(@PathVariable Long locationId) {
        LocationSummaryDTO result = locationService.getLocationSummaryById(locationId );
        return ResponseEntity.ok(result);
    }
}
