package com.zopato.controller;

import com.zopato.model.IngredientCategory;
import com.zopato.model.IngredientsItem;
import com.zopato.request.IngredientCategoryRequest;
import com.zopato.request.IngredientItemRequest;
import com.zopato.service.IngredientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/category")
    ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req)
            throws Exception {
        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(req.getName(),
                req.getRestaurantId());

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping()
    ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req)
            throws Exception {
        IngredientsItem ingredientsItem = ingredientService.createIngredientsItem(req.getRestaurantId(), req.getName(),
                req.getCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    ResponseEntity<IngredientsItem> createIngredientItem(@PathVariable Long id)
            throws Exception {

        IngredientsItem ingredientsItem = ingredientService.updateStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<IngredientCategory> getIngredientCategoryById(@PathVariable Long id)
            throws Exception {
        IngredientCategory ingredientCategory = ingredientService.findIngredientCategoryById(id);

        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{id}")
    ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(@PathVariable Long id)
            throws Exception {
        List<IngredientsItem> items = ingredientService.findRestaurantIngredients(id);

        return new ResponseEntity<>(items, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{id}/category")
    ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id)
            throws Exception {
        List<IngredientCategory> items = ingredientService.findIngredientCategoriesByRestaurantId(id);

        return new ResponseEntity<>(items, HttpStatus.CREATED);
    }

}
