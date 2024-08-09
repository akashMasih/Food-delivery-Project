package com.zopato.request;

import java.util.List;

import com.zopato.model.FoodCategory;
import com.zopato.model.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {

    private String name;

    private String description;
    private Long price;
    private List<String> images;
    private FoodCategory category;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;

}
