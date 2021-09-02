package com.example.graphQLdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.graphQLdemo.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

	List<OrderModel> findByCustomerId(Long customerId);

	@Query(value = "select o.id as orderId,o.quantity,p.id as productId,p.name,p.description from orders o left outer join products p on "
			+ "o.product_id=p.id where o.customer_id = :customerId",nativeQuery=true)
	List<Object[]> findOrderWithProductByCustomerId(@Param("customerId") Long customerId);
}
