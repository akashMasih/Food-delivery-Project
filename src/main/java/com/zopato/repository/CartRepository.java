package com.zopato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByCustomerId(Long customerId);

}
