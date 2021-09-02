package com.example.graphQLdemo.resolver;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphQLdemo.dto.CreateOrderDTO;
import com.example.graphQLdemo.dto.CustomerDTO;
import com.example.graphQLdemo.dto.OrderDTO;
import com.example.graphQLdemo.dto.ProductDTO;
import com.example.graphQLdemo.model.CustomerModel;
import com.example.graphQLdemo.model.OrderModel;
import com.example.graphQLdemo.model.ProductModel;
import com.example.graphQLdemo.repository.CustomerRepository;
import com.example.graphQLdemo.repository.OrderRepository;
import com.example.graphQLdemo.repository.ProductRepository;

@Component
public class OrderMutationResolver implements GraphQLMutationResolver {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductRepository productRepository;

	public OrderDTO createOrder(CreateOrderDTO createOrderDTO) {
		Optional<CustomerModel> customer =  customerRepository
                .findById(Long.parseLong(createOrderDTO.getCustomerId()));
		Optional<ProductModel> product = productRepository
                .findById(Long.parseLong(createOrderDTO.getProductId()));
		if(!customer.isPresent() || !product.isPresent() )
			return null;
		OrderModel order = new OrderModel();
		order.setCustomer(customer.get());
		order.setProduct(product.get());
		order.setQuantity(createOrderDTO.getQuantity());
		order.setStatus("PENDING");
		order = orderRepository.save(order);
		return orderToGraphQL(order);
	}

	private OrderDTO orderToGraphQL(OrderModel orderModel) {
		OrderDTO order = new OrderDTO();
		order.setId(orderModel.getId().toString());
		order.setStatus(orderModel.getStatus());
		order.setQuantity(orderModel.getQuantity());
		ProductDTO product = new ProductDTO();
		product.setId(orderModel.getProduct().getId().toString());
		order.setProduct(product);
		CustomerDTO customer = new CustomerDTO();
		customer.setId(orderModel.getCustomer().getId().toString());
		order.setCustomer(customer);
		return order;
	}

}
