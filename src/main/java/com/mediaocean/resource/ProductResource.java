package com.mediaocean.resource;

import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import com.mediaocean.controller.ProductController;
import com.mediaocean.model.Product;

public class ProductResource {
	public static Resource<Product> getProductResource(Product product){
		Resource<Product> product_resource = new Resource<Product>(product);
		product_resource.add(
				linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel()
		);
		return product_resource;
	}
	public static List<Resource<Product>> getProductResources(List<Product> products){
		List<Resource<Product>> products_resource=  new ArrayList<>();	
		products.stream().forEach((product)->{
			products_resource.add(ProductResource.getProductResource(product));
		});
		return products_resource;
	}
}
