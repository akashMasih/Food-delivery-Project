package com.zopato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zopato.model.Food;
import com.zopato.model.Restaurant;
import com.zopato.model.User;
import com.zopato.request.CreateFoodRequest;
import com.zopato.response.MessageResponse;
import com.zopato.service.FoodService;
import com.zopato.service.RestaurantService;
import com.zopato.service.UserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.getUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long foodId) throws Exception {
        foodService.deleteFood(foodId);
        MessageResponse response = new MessageResponse();
        response.setMessage("Food deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFood(@RequestBody CreateFoodRequest req,
            @RequestHeader("Authorization") String jwt, @PathVariable Long foodId) throws Exception {

        return null;
    }

    @PutMapping("/{foodId}/status")
    public ResponseEntity<Food> updateFoodAvailability(@RequestHeader("Authorization") String jwt,
            @PathVariable Long foodId) throws Exception {

        User user = userService.getUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(foodId);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
