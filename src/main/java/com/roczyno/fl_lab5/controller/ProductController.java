package com.roczyno.fl_lab5.controller;

import com.roczyno.fl_lab5.dto.ProductDto;
import com.roczyno.fl_lab5.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
		return ResponseEntity.ok(productService.createProduct(productDto));
	}


	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId){
		return ResponseEntity.ok(productService.getProduct(productId));
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> getProducts(){
		return ResponseEntity.ok(productService.getProducts());
	}


	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto request){
		return ResponseEntity.ok(productService.updateProduct(productId,request));
	}


	@PreAuthorize("hasRole('USER') and hasAuthority('DELETE') ")
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
		return ResponseEntity.ok(productService.deleteProduct(productId));
	}


}
