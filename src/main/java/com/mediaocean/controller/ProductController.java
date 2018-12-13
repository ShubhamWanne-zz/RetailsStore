package com.mediaocean.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mediaocean.exceptions.CustomerNotFoundException;
import com.mediaocean.model.Product;
import com.mediaocean.repository.CustomerRepository;
import com.mediaocean.repository.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CustomerRepository customerRepo;
	
	@RequestMapping(path="/customers/{customer_id}/products", method=RequestMethod.POST)
	public Product addProduct(@PathVariable Long customer_id, @RequestBody Product product) {
		
		return customerRepo.findById(customer_id).map(customer->{
			product.setCustomer(customer);
			return productRepo.save(product);
		}).orElseThrow(()-> new CustomerNotFoundException("Customer with id "+customer_id+" not found."));
		
	}
}
