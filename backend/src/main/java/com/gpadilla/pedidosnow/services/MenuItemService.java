package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.MenuItem;
import com.gpadilla.pedidosnow.domain.MenuItemCategory;
import com.gpadilla.pedidosnow.domain.RestaurantLocation;
import com.gpadilla.pedidosnow.dtos.CreateMenuItemRequestDTO;
import com.gpadilla.pedidosnow.dtos.GetMenuItemDetailsDTO;
import com.gpadilla.pedidosnow.dtos.MenuItemMapper;
import com.gpadilla.pedidosnow.repositories.MenuItemCategoryRepository;
import com.gpadilla.pedidosnow.repositories.MenuItemRepository;
import com.gpadilla.pedidosnow.repositories.RestaurantLocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantLocationRepository locationRepository;
    private final MenuItemCategoryRepository menuItemCategoryRepository;

    public void deleteMenuItem(String auth0Id, Long menuItemId) {
        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!item.getLocation().getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to delete this item");

        menuItemRepository.delete(item);
    }

    public GetMenuItemDetailsDTO createMenuItem(String auth0Id, Long locationId, CreateMenuItemRequestDTO createMenuItemRequestDTO) {
        RestaurantLocation location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!location.getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to add items to this location");

        MenuItemCategory category = menuItemCategoryRepository.findById(createMenuItemRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        MenuItem menuItem = MenuItem.builder()
                .name(createMenuItemRequestDTO.getName())
                .description(createMenuItemRequestDTO.getDescription())
                .price(createMenuItemRequestDTO.getPrice())
                .imageUrl(createMenuItemRequestDTO.getImageUrl())
                .available(false)
                .category(category)
                .location(location)
                .build();

        menuItemRepository.save(menuItem);

        return MenuItemMapper.toMenuItemDetailsDTO(menuItem);
    }

    public GetMenuItemDetailsDTO updateMenuItem(String auth0Id, Long itemId, CreateMenuItemRequestDTO updateMenuItemRequestDTO) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!item.getLocation().getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to edit this item's details");

        MenuItemCategory category = menuItemCategoryRepository.findById(updateMenuItemRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        item.setName(updateMenuItemRequestDTO.getName());
        item.setDescription(updateMenuItemRequestDTO.getDescription());
        item.setCategory(category);
        item.setPrice(updateMenuItemRequestDTO.getPrice());
        item.setImageUrl(updateMenuItemRequestDTO.getImageUrl());

        menuItemRepository.save(item);

        return MenuItemMapper.toMenuItemDetailsDTO(item);
    }

    public void setItemIsAvailable(String auth0Id, Long itemId, boolean isAvailable) {
        MenuItem item = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!item.getLocation().getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this item");

        item.setAvailable(isAvailable);

        menuItemRepository.save(item);
    }
}
