package com.zopato.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zopato.model.IngredientCategory;
import com.zopato.model.IngredientsItem;
import com.zopato.model.Restaurant;
import com.zopato.repository.IngredientCategoryRepository;
import com.zopato.repository.IngredientItemRepository;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        IngredientCategory ingredientCategory = new IngredientCategory();
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        if (!ingredientCategory.isPresent()) {
            throw new Exception("Ingredient Category not found with this id " + id);
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long restaurantId) throws Exception {
        restaurantService.findRestaurantById(restaurantId);
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId)
            throws Exception {
        IngredientsItem ingredientsItem = new IngredientsItem();
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = ingredientCategoryRepository.findById(categoryId).get();
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setCategory(category);

        return ingredientItemRepository.save(ingredientsItem);
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        IngredientsItem ingredientsItem = ingredientItemRepository.findById(id).get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientItemRepository.save(ingredientsItem);
    }

}
