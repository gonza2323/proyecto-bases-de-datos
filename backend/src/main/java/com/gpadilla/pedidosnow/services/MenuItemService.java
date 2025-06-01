package com.gpadilla.pedidosnow.services;

import com.gpadilla.pedidosnow.domain.MenuItem;
import com.gpadilla.pedidosnow.repositories.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public void deleteMenuItem(String auth0Id, Long menuItemId) {
        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!item.getLocation().getRestaurant().getAuth0Id().equals(auth0Id))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to delete this item");

        menuItemRepository.delete(item);
    }
}
