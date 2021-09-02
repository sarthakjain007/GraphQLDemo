package com.example.graphQLdemo.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphQLdemo.dto.ProductDTO;
import com.example.graphQLdemo.model.ProductModel;
import com.example.graphQLdemo.repository.ProductRepository;

@Component
public class ProductResolver implements GraphQLQueryResolver{
	
	@Autowired
    private ProductRepository productRepository;
	
	public ProductDTO productById(String id) {
        return productRepository
                .findById(Long.parseLong(id))
                .map(this::modelToGraphQL)
                .orElse(null);
    }

    private ProductDTO modelToGraphQL(ProductModel productModel) {
    	ProductDTO product = new ProductDTO();
    	product.setId(productModel.getId().toString());
    	product.setName(productModel.getName());
    	product.setDescription(productModel.getDescription());
    	product.setPrice(productModel.getPrice());
        return product;
    }
	

}
