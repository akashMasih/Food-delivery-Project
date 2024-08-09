package com.zopato.reposetory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

    public List<FoodCategory> findByRestaurantId(Long restaurantId);

}
