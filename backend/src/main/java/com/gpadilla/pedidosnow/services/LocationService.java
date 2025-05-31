package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.Address;
import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.dtos.CreateAddressDTO;
import com.gpadilla.pedidosnow.dtos.CreateLocationRequestDTO;
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

@AllArgsConstructor
@Service
public class LocationService {

    private final RestaurantLocationRepository locationRepository;
    private final UserService userService;

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

    public void createLocation(String auth0Id, CreateLocationRequestDTO createLocationRequestDTO) {
        CreateAddressDTO requestedAddress = createLocationRequestDTO.getAddress();

        Address address = Address.builder()
                .province(requestedAddress.getProvince())
                .municipio(requestedAddress.getMunicipio())
                .localidad(requestedAddress.getLocalidad())
                .street(requestedAddress.getStreet())
                .number(requestedAddress.getNumber())
                .floorNo(requestedAddress.getFloor())
                .apartmentNo(requestedAddress.getApartment())
                .phoneNumber(requestedAddress.getPhoneNumber())
                .obervation(requestedAddress.getObservation())
                .latitude(requestedAddress.getLatitude())
                .longitude(requestedAddress.getLongitude())
                .build();

        Restaurant restaurant = userService.getRestaurantByAuth0Id(auth0Id).get();

        RestaurantLocation location = RestaurantLocation.builder()
                .locationName(createLocationRequestDTO.getName())
                .restaurant(restaurant)
                .address(address)
                .isOpen(false)
                .build();

        locationRepository.save(location);
    }

    // TODO get location details with menu items
}
