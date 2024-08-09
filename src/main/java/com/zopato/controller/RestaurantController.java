package com.zopato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zopato.dto.RestaurantDto;
import com.zopato.model.Restaurant;
import com.zopato.model.User;
import com.zopato.service.RestaurantService;
import com.zopato.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization") String jwt,
            @RequestParam String query) throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        List<Restaurant> restaurant = restaurantService.searchRestaurants(query);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurant(@RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        List<Restaurant> restaurant = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}/add-favorite")
    public ResponseEntity<RestaurantDto> addToFavorite(@RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user = userService.getUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavorite(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
