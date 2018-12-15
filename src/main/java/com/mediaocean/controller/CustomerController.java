package com.mediaocean.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mediaocean.exceptions.CustomerNotFoundException;
import com.mediaocean.model.Customer;
import com.mediaocean.repository.CustomerRepository;
import com.mediaocean.resource.CustomerResource;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepo;	
	
	@RequestMapping(path="/customers", method= RequestMethod.POST)
	public Customer addCustomer(@Valid @RequestBody Customer customer) {
		return customerRepo.save(customer);
	}
	
	@RequestMapping(path="/customers/{customerId}", method= RequestMethod.GET)
	public Resource<Customer> getCustomer(@PathVariable Long customerId) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Unable to find customer with id : "+customerId));		
		return CustomerResource.getCustomerResource(customer);
	}
	
	@RequestMapping(path="/customers/{customerId}", method= RequestMethod.PUT)
	public Resource<Customer> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer reqCustomer) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Unable to find customer with id : "+customerId));		
		customer.setName(reqCustomer.getName());
		return CustomerResource.getCustomerResource(customerRepo.save(customer));
	}
	
}
