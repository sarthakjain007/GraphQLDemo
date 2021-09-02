package com.example.graphQLdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graphQLdemo.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

}
