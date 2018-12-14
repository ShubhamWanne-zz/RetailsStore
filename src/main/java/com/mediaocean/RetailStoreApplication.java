package com.mediaocean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.mediaocean.repository.CustomerRepository;
import com.mediaocean.repository.ProductRepository;

@EnableJpaAuditing
@SpringBootApplication
public class RetailStoreApplication{

	@Autowired
	ProductRepository productRepo;
	@Autowired
	CustomerRepository customerRepo;

	public static void main(String[] args) {
		SpringApplication.run(RetailStoreApplication.class, args);
	}

}

