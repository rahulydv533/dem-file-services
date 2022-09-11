package com.demo.file.demofileservices.dao;

import com.demo.file.demofileservices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
