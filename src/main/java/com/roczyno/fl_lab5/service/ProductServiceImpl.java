package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.dto.ProductDto;
import com.roczyno.fl_lab5.model.Product;
import com.roczyno.fl_lab5.repository.ProductRepository;
import com.roczyno.fl_lab5.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	@Override
	public ProductDto createProduct(ProductDto request) {
		Product product= Product.builder()
				.name(request.name())
				.description(request.description())
				.createdAt(LocalDateTime.now())
				.build();
		Product savedProduct= productRepository.save(product);
		return productMapper.mapToDto(savedProduct);
	}

	@Override
	public ProductDto updateProduct(Long productId, ProductDto request) {
		Product product= productRepository.findById(productId).orElseThrow();
		if(request.name()!=null){
			product.setName(request.name());
		}
		if(request.description()!=null){
			product.setDescription(request.description());
		}
		Product updatedProduct=productRepository.save(product);
		return productMapper.mapToDto(updatedProduct);
	}

	@Override
	public String deleteProduct(Long productId) {
		Product product= productRepository.findById(productId).orElseThrow();
		productRepository.delete(product);
		return "product deleted successfully";
	}

	@Override
	public ProductDto getProduct(Long productId) {
		Product product=productRepository.findById(productId).orElseThrow();
		return productMapper.mapToDto(product);
	}

	@Override
	public List<ProductDto> getProducts() {
		List<Product> products= productRepository.findAll();
		return  products.stream()
				.map(productMapper::mapToDto)
				.toList();
	}
}
