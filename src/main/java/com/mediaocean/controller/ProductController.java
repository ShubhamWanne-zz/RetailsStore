package com.mediaocean.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mediaocean.exceptions.CustomerNotFoundException;
import com.mediaocean.exceptions.ProductNotFoundException;
import com.mediaocean.model.Product;
import com.mediaocean.repository.CustomerRepository;
import com.mediaocean.repository.ProductRepository;
import com.mediaocean.resource.ProductResource;

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
	
	@RequestMapping(path= "/customers/{customer_id}/products" , method=RequestMethod.GET)
	public List<Resource<Product>> getProducts(@PathVariable Long customer_id){
		if(!customerRepo.existsById(customer_id)) throw new CustomerNotFoundException("Customer with id "+customer_id+" not found.");
		List<Resource<Product>> products_resource = ProductResource.getProductResources(productRepo.findByCustomerId(customer_id));
		return products_resource;
	}

	@RequestMapping(path="/products/{product_id}", method= RequestMethod.GET)
	public Resource<Product> getProduct(@PathVariable Long product_id){
		Product product = productRepo.findById(product_id).orElseThrow(()->new ProductNotFoundException("Product with id "+product_id+" not found."));
		return ProductResource.getProductResource(product);
	}
	
}
