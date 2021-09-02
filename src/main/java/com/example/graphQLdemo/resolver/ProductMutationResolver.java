package com.example.graphQLdemo.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphQLdemo.dto.CreateProductDTO;
import com.example.graphQLdemo.dto.ProductDTO;
import com.example.graphQLdemo.model.ProductModel;
import com.example.graphQLdemo.repository.ProductRepository;

@Component
public class ProductMutationResolver implements GraphQLMutationResolver {

	@Autowired
	private ProductRepository productRepository;

	public ProductDTO createProduct(CreateProductDTO productDTO) {
		ProductModel product = new ProductModel();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		product = productRepository.save(product);
		return covertToDTO(product);
	}

	private ProductDTO covertToDTO(ProductModel productModel) {
		ProductDTO product = new ProductDTO();
		product.setId(productModel.getId().toString());
		product.setName(productModel.getName());
		product.setPrice(productModel.getPrice());
		product.setDescription(productModel.getDescription());
		return product;
	}
}
