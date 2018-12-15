package com.mediaocean.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediaocean.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByCustomerId(Long id);
	List<Product> deleteByCustomerId(Long id);
}
