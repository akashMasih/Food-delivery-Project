package com.zopato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // custom methods
    public User findByEmail(String username);

}
