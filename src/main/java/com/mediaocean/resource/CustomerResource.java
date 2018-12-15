package com.mediaocean.resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import com.mediaocean.controller.CustomerController;
import com.mediaocean.model.Customer;

public class CustomerResource {
	public static Resource<Customer> getCustomerResource(Customer customer){
		Resource<Customer> customer_resource =  new Resource<Customer>(customer);
		customer_resource.add(
				linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel()
		);
		return customer_resource;
	}
}
