package com.zopato.service;

import java.util.List;
import com.zopato.model.Food;
import com.zopato.model.FoodCategory;
import com.zopato.model.Restaurant;
import com.zopato.request.CreateFoodRequest;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant);

    public void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isSeasonal, String category);

    public List<Food> searchFood(String query);

    public Food updateFood(Long foodId, CreateFoodRequest req) throws Exception;

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus(Long foodId) throws Exception;

}
