package com.demo.file.demofileservices.dao;

import com.demo.file.demofileservices.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByName(String name);


}
