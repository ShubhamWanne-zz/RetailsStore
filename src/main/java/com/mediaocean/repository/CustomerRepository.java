package com.mediaocean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediaocean.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
