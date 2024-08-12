package com.zopato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
