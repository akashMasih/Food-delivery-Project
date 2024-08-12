package com.zopato.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zopato.model.FoodCategory;
import com.zopato.model.Restaurant;
import com.zopato.repository.FoodCategoryRepository;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public FoodCategory createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
        FoodCategory category = new FoodCategory();
        category.setName(name);
        category.setRestaurant(restaurant);
        return foodCategoryRepository.save(category);

    }

    @Override
    public List<FoodCategory> getCategoriesByRestaurant(Long userId) throws Exception {

        Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);

        return foodCategoryRepository.findByRestaurantId(restaurant.getId());


    }

    @Override
    public FoodCategory findCategoryById(Long id) throws Exception {
        Optional<FoodCategory> category = foodCategoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new Exception("Category not found");
        }
    }

}
