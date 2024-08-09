package com.zopato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zopato.model.FoodCategory;
import com.zopato.model.User;
import com.zopato.service.FoodCategoryService;
import com.zopato.service.UserService;

@RestController
@RequestMapping("/api/admin/foodCategory")
public class FoodCategoryController {

    @Autowired
    private FoodCategoryService foodCategoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<FoodCategory> createFoodCategory(@RequestHeader("Authorization") String jwt,
            FoodCategory foodCategory)
            throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        FoodCategory foodCategory1 = foodCategoryService.createCategory(foodCategory.getName(), user.getId());

        return new ResponseEntity<>(foodCategory1, HttpStatus.CREATED);
    }

    @GetMapping("/{foodCategoryId}")
    public ResponseEntity<FoodCategory> getFoodCategoryById(@RequestHeader("Authorization") String jwt,
            Long foodCategoryId) throws Exception {

        User user = userService.getUserByJwtToken(jwt);
        FoodCategory foodCategory = foodCategoryService.findCategoryById(foodCategoryId);

        return new ResponseEntity<>(foodCategory, HttpStatus.OK);

    }

    @GetMapping("/{foodCategoryId}")
    public ResponseEntity<List<FoodCategory>> getFoodCategoryByRestaurantId(@RequestHeader("Authorization") String jwt,
            Long restaurantId) throws Exception {

        User user = userService.getUserByJwtToken(jwt);

        List<FoodCategory> foodCategory = foodCategoryService.getCategoriesByRestaurantId(restaurantId);
        return new ResponseEntity<>(foodCategory, HttpStatus.OK);

    }

}
