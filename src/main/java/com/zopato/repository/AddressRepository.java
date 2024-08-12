package com.zopato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
