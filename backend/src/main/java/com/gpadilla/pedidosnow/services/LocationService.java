package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.dtos.LocationDetailsDTO;
import com.gpadilla.pedidosnow.dtos.LocationMapper;
import com.gpadilla.pedidosnow.repositories.RestaurantLocationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Service
public class LocationService {

    private final RestaurantLocationRepository locationRepository;

    public List<LocationDetailsDTO> getAllLocations() {
        List<RestaurantLocation> locations = locationRepository.findAll();

        return locations.stream()
                .map(LocationMapper::toLocationDetailsDTO)
                .collect(Collectors.toList());
    }

    public LocationDetailsDTO getLocationById(Long locationId) {
        Optional<RestaurantLocation> locationOpt = locationRepository.findById(locationId);

        if (locationOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant location not found");

        return LocationMapper.toLocationDetailsDTO(locationOpt.get());
    }

    // TODO get location details with menu items
}
