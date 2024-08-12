package com.zopato.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zopato.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    @Query("SELECT f from Food f where lower(f.name) LIKE lower(concat('%', :query, '%')) OR lower(f.description) LIKE lower(concat('%', :query, '%'))")
    List<Food> searchFood(@Param("query") String query);

}
