package com.mediaocean.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mediaocean.exceptions.CustomerNotFoundException;
import com.mediaocean.model.Bill;
import com.mediaocean.repository.BillRepository;
import com.mediaocean.repository.CustomerRepository;

@RestController
public class BillController {

	@Autowired
	private BillRepository billrepo;
	@Autowired
	private CustomerRepository customerRepo;
	
	@RequestMapping(path="/customers/{customer_id}/bills", method=RequestMethod.POST)
	private Bill addBill(@PathVariable Long customer_id, @Valid @RequestBody Bill bill) {
		return customerRepo.findById(customer_id).map(customer -> {
			bill.setCustomer(customer);
			return billrepo.save(bill);
		}).orElseThrow(()->new CustomerNotFoundException("Customer with id "+customer_id+" not found."));
	}
	
}
