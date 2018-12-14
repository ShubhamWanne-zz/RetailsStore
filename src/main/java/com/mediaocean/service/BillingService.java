package com.mediaocean.service;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import com.mediaocean.model.Product;

@Service
public class BillingService {
	static Double total_cost;
	public static Double getTotalCost(List<Resource<Product>> products){
		total_cost = 0.0;
		products.forEach(productResource -> {
			Product product= productResource.getContent();
			switch(product.getCategory().toString()) {
			case "A":
					total_cost += getDiscountedCost(product.getCost(), 0.1, product.getQuantity());
					break;
			case "B": 
					total_cost += getDiscountedCost(product.getCost(), 0.2, product.getQuantity());
					break;
			case "C": 
					total_cost += getDiscountedCost(product.getCost(), 0.0, product.getQuantity());
			}
			
		});
		return total_cost;
	}
	public static Double getDiscountedCost(Double cost, Double discount, int quantity) {
		return (cost + (cost * discount)) * quantity;
	}
}
