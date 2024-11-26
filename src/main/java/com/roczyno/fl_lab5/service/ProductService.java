package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.dto.ProductDto;

import java.util.List;

public interface ProductService {
	ProductDto createProduct(ProductDto request);
	ProductDto updateProduct(Long productId,ProductDto request);
	String deleteProduct(Long productId);
	ProductDto getProduct(Long productId);
	List<ProductDto> getProducts();
}
