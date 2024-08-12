package com.zopato.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zopato.model.Food;
import com.zopato.model.FoodCategory;
import com.zopato.model.Restaurant;
import com.zopato.repository.FoodRepository;
import com.zopato.request.CreateFoodRequest;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant) {
        Food food = new Food();
        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        Food saveFood = foodRepository.save(food);
        restaurant.getFoods().add(saveFood);

        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {

        Food food = foodRepository.findById(foodId).get();
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isSeasonal, String category) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isSeasonal) {
            foods = filterFoodBySeasonal(foods, isVegetarian);
        }
        if (category != null && !category.equals("")) {
            foods = filterFoodByCategory(foods, category);
        }
        return foods;
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegetarian).toList();
    }

    private List<Food> filterFoodBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).toList();
    }

    private List<Food> filterFoodByCategory(List<Food> foods, String category) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(category);
            }
            return false;
        }).toList();
    }

    @Override
    public List<Food> searchFood(String query) {
        return foodRepository.searchFood(query);
    }

    @Override
    public Food updateFood(Long foodId, CreateFoodRequest req) throws Exception {
        Food food = foodRepository.findById(foodId).get();

        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        food.setIngredients(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        return foodRepository.save(food);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()) {
            throw new Exception("Food not Exist" + foodId);
        }
        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = foodRepository.findById(foodId).get();
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

}
