package com.example.graphQLdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graphQLdemo.model.CustomerModel;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

}
