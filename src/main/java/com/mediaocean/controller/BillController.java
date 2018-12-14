package com.mediaocean.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mediaocean.exceptions.CustomerNotFoundException;
import com.mediaocean.model.Bill;
import com.mediaocean.model.Customer;
import com.mediaocean.model.Product;
import com.mediaocean.repository.BillRepository;
import com.mediaocean.repository.CustomerRepository;
import com.mediaocean.repository.ProductRepository;
import com.mediaocean.resource.ProductResource;
import com.mediaocean.service.BillingService;

@RestController
public class BillController {

	@Autowired
	private BillRepository billrepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ProductRepository productRepo;
	
	@RequestMapping(path="/customers/{customer_id}/bill/checkout", method=RequestMethod.GET)
	private Map<String, Object> createBill(@PathVariable Long customer_id){
		Map<String , Object> checkout_details = new HashMap<>();
		Customer customer =  customerRepo.findById(customer_id).orElseThrow(()->new CustomerNotFoundException("Customer with id "+customer_id+" not found."));
		List<Resource<Product>> products = ProductResource.getProductResources(productRepo.findByCustomerId(customer_id));

		if(products.size() == 0) {
			checkout_details.put("total_count", 0);
			checkout_details.put("msg", "No products found.");
			return checkout_details;
		}
		
		Bill bill = new Bill();
		bill.setCustomer(customer);		
		
		checkout_details.put("total_cost", BillingService.getTotalCost(products));

		bill.setCost((Double)checkout_details.get("total_cost"));
		
		createBill(bill);
		
		checkout_details.put("bill_id", bill.getId());
		checkout_details.put("customer_id", customer.getId());
		checkout_details.put("customer_name", customer.getName());		
		checkout_details.put("total_count", products.size());
		checkout_details.put("products", products);
		
		return checkout_details;
	}
	
	private boolean createBill(Bill bill) {
		try {			
			bill = billrepo.save(bill);
			return true;
		}catch(DataIntegrityViolationException ex) {
			return false;
		}
	}
}
