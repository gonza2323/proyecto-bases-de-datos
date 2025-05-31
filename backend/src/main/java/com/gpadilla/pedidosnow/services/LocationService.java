package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.Address;
import com.gpadilla.pedidosnow.domain.Restaurant;
import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.dtos.*;
import com.gpadilla.pedidosnow.repositories.RestaurantLocationRepository;
import lombok.AllArgsConstructor;
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

    public List<LocationSummaryDTO> getAllLocations() {
        List<RestaurantLocation> locations = locationRepository.findAll();

        return locations.stream()
                .map(LocationMapper::toLocationDetailsDTO)
                .collect(Collectors.toList());
    }

    public LocationSummaryDTO getLocationSummaryById(Long locationId) {
        Optional<RestaurantLocation> locationOpt = locationRepository.findById(locationId);

        if (locationOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant location not found");

        return LocationMapper.toLocationDetailsDTO(locationOpt.get());
    }

    public GetLocationDetailsDTO getLocationDetailsById(Long locationId, String auth0Id) {
        RestaurantLocation location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!location.getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this location's details");

        Address address = location.getAddress();

        GetAddressDetailsDTO addressDetails = GetAddressDetailsDTO.builder()
                .province(address.getProvince())
                .municipio(address.getMunicipio())
                .localidad(address.getLocalidad())
                .street(address.getStreet())
                .number(address.getNumber())
                .floor(address.getFloorNo())
                .apartment(address.getApartmentNo())
                .phoneNumber(address.getPhoneNumber())
                .observation(address.getObervation())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();

        GetLocationDetailsDTO locationDetails = GetLocationDetailsDTO.builder()
                .name(location.getLocationName())
                .address(addressDetails)
                .build();

        return locationDetails;
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


    public void updateLocation(String auth0Id, Long locationId, CreateLocationRequestDTO updateLocationRequestDTO) {
        RestaurantLocation location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!location.getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to modify this location");

        Address address = location.getAddress();
        CreateAddressDTO newAddress = updateLocationRequestDTO.getAddress();

        location.setLocationName(updateLocationRequestDTO.getName());
        address.setProvince(newAddress.getProvince());
        address.setMunicipio(newAddress.getMunicipio());
        address.setLocalidad(newAddress.getLocalidad());
        address.setStreet(newAddress.getStreet());
        address.setNumber(newAddress.getNumber());
        address.setFloorNo(newAddress.getFloor());
        address.setApartmentNo(newAddress.getApartment());
        address.setPhoneNumber(newAddress.getPhoneNumber());
        address.setObervation(newAddress.getObservation());
        address.setLatitude(newAddress.getLatitude());
        address.setLongitude(newAddress.getLongitude());

        locationRepository.save(location);
    }

    public void deleteLocation(String auth0Id, Long locationId) {
        RestaurantLocation location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!location.getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to delete this location");

        locationRepository.delete(location);
    }

    // TODO get location details with menu items
}
