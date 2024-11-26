package com.roczyno.fl_lab5.repository;

import com.roczyno.fl_lab5.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
