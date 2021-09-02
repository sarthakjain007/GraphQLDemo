package com.example.graphQLdemo.resolver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphQLdemo.dto.OrderDTO;
import com.example.graphQLdemo.dto.ProductDTO;
import com.example.graphQLdemo.model.OrderModel;
import com.example.graphQLdemo.repository.OrderRepository;

@Component
public class OrderResolver implements GraphQLQueryResolver{

	@Autowired
	private OrderRepository orderRepository;

	public List<OrderDTO> getAllOrdersWithoutProductDetails(String id) {
		List<OrderDTO> ordersDTOList = null;
		List<OrderModel> orders = orderRepository.findByCustomerId(Long.parseLong(id));
		if (!CollectionUtils.isEmpty(orders)) {
			ordersDTOList = convertToDTOWithoutProduct(orders);
		}
		return ordersDTOList;
	}

	private List<OrderDTO> convertToDTOWithoutProduct(List<OrderModel> orders) {
		List<OrderDTO> ordersList = new ArrayList<>();
		for (OrderModel order : orders) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setId(order.getId().toString());
			orderDTO.setQuantity(order.getQuantity());
			orderDTO.setStatus(order.getStatus());
			ordersList.add(orderDTO);
		}
		return ordersList;
	}

	public List<OrderDTO> getAllOrdersWithProductDetails(String id) {
		List<OrderDTO> ordersDTOList = null;
		List<Object[]> orders = orderRepository.findOrderWithProductByCustomerId(Long.parseLong(id));
		if (!CollectionUtils.isEmpty(orders)) {
			ordersDTOList = convertToDTOWithProduct(orders);
		}
		return ordersDTOList;
	}

	private List<OrderDTO> convertToDTOWithProduct(List<Object[]> orders) {
		List<OrderDTO> ordersList = new ArrayList<>();
		try {
			for (Object[] obj : orders) {
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setId(null != obj[0] ? obj[0].toString() : null);
				orderDTO.setQuantity(null != obj[1] ? Integer.parseInt(obj[1].toString()) : null);
				ProductDTO productDTO = new ProductDTO();
				productDTO.setId(null != obj[2] ? obj[2].toString() : null);
				productDTO.setName(null != obj[3] ? obj[3].toString() : null);
				productDTO.setDescription(null != obj[4] ? obj[4].toString() : null);
				orderDTO.setProduct(productDTO);
				ordersList.add(orderDTO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ordersList;
	}

}
