package com.mediaocean.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediaocean.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
