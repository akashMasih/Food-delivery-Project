package com.zopato.service;

import java.util.List;

import com.zopato.model.FoodCategory;

public interface FoodCategoryService {

    public FoodCategory createCategory(String name, Long userId) throws Exception;

    // public void deleteCategory(Long id) throws Exception;

    public List<FoodCategory> getCategoriesByRestaurantId(Long restaurantId);

    public FoodCategory findCategoryById(Long id) throws Exception;

    // public FoodCategory updateCategory(Long id, String name) throws Exception;

}
