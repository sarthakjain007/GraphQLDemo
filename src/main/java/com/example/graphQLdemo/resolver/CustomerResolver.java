package com.example.graphQLdemo.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphQLdemo.dto.CustomerDTO;
import com.example.graphQLdemo.model.CustomerModel;
import com.example.graphQLdemo.repository.CustomerRepository;

@Component
public class CustomerResolver implements GraphQLQueryResolver{
	
	@Autowired
	CustomerRepository customerRepository;
	
	public CustomerDTO customerById(String id) {
        return customerRepository
                .findById(Long.parseLong(id))
                .map(this::modelToGraphQL)
                .orElse(null);
    }

    private CustomerDTO modelToGraphQL(CustomerModel customerModel) {
    	CustomerDTO customer = new CustomerDTO();
        customer.setId(customerModel.getId().toString());
        customer.setName(customerModel.getName());
        customer.setEmail(customerModel.getEmail());
        return customer;
    }
}
