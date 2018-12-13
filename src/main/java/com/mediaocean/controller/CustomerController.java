package com.mediaocean.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mediaocean.model.Customer;
import com.mediaocean.repository.CustomerRepository;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@RequestMapping(path="/customers", method= RequestMethod.POST)
	public Customer addCustomer(@Valid @RequestBody Customer customer) {
		return customerRepo.save(customer);
	}
}
