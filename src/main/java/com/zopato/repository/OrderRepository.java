package com.zopato.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomer(Long userId);

    public List<Order> findByRestaurantId(Long restaurantId);

}
