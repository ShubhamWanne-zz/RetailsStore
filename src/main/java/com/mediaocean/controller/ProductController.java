package com.mediaocean.controller;

import java.util.List;

import javax.validation.Valid;

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
	
	@RequestMapping(path="/customers/{customerId}/products/{productsId}", method=RequestMethod.PUT)
	public Resource<Product> updateProductDetails(@PathVariable Long customerId, 
												@PathVariable Long productsId, 
												@Valid@RequestBody Product product){
		if(!customerRepo.existsById(customerId)) throw new CustomerNotFoundException("Customer with id "+customerId+" not found.");

		Product productTemp = productRepo.findByCustomerId(customerId).stream().
												filter(item-> item.getId() == productsId).
												findFirst().
												orElseThrow(()-> new ProductNotFoundException("Unable to find Product of id = "+productsId));
		productTemp.setName(product.getName());
		productTemp.setCategory(product.getCategory());
		productTemp.setCost(product.getCost());
		productTemp.setQuantity(product.getQuantity());	
		
		return ProductResource.getProductResource(productRepo.save(productTemp), customerId);
	}
	
	
	@RequestMapping(path= "/customers/{customer_id}/products" , method=RequestMethod.GET)
	public List<Resource<Product>> getProducts(@PathVariable Long customer_id){
		if(!customerRepo.existsById(customer_id)) throw new CustomerNotFoundException("Customer with id "+customer_id+" not found.");
		List<Resource<Product>> products_resource = ProductResource.getProductResources(productRepo.findByCustomerId(customer_id), customer_id);
		return products_resource;
	}

	@RequestMapping(path="customer/{customer_id}/products/{product_id}", method= RequestMethod.GET)
	public Resource<Product> getProduct(@PathVariable Long customer_id, @PathVariable Long product_id){
		Product product = productRepo.findByCustomerId(customer_id).stream().
				filter(item-> item.getId() == product_id).
				findFirst().
				orElseThrow(()-> new ProductNotFoundException("Unable to find Product of id = "+product_id));
		return ProductResource.getProductResource(product, customer_id);
	}
	
}
