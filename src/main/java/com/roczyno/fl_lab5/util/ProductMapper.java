package com.roczyno.fl_lab5.util;

import com.roczyno.fl_lab5.dto.ProductDto;
import com.roczyno.fl_lab5.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
	public ProductDto mapToDto(Product savedProduct) {
		return new ProductDto(
				savedProduct.getName(),
				savedProduct.getDescription(),
				savedProduct.getCreatedAt()
		);
	}
}
