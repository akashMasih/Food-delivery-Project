package com.zopato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zopato.model.Food;
import com.zopato.model.User;
import com.zopato.service.FoodService;
import com.zopato.service.RestaurantService;
import com.zopato.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    ResponseEntity<List<Food>> searchFood(@RequestParam String query, @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        List<Food> food = foodService.searchFood(query);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
            @RequestParam boolean isVegetarian, @RequestParam boolean isSeasonal,
            @RequestParam(required = false) String foodCategory, @RequestHeader("Authorization") String jwt)
            throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        List<Food> food = foodService.getRestaurantFood(restaurantId, isVegetarian, isSeasonal, foodCategory);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
