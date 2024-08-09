package com.zopato.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zopato.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
