package com.zopato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
