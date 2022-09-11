package com.demo.file.demofileservices.controller;

import com.demo.file.demofileservices.dao.ProductRepository;
import com.demo.file.demofileservices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "/insertProduct", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> createStudent(@RequestParam MultipartFile file, @RequestParam String name,
                                                 @RequestParam String description, @RequestParam String price) {

        String filename = file.getOriginalFilename();
        Path path = Paths.get("products/" + filename);
        Product product = Product.builder().name(name)
                .description(description).price(price)
                .fileName(filename)
                .file_path(path.toAbsolutePath().toString())
                .createdAt(LocalDateTime.now().toString()).updatedAt(null).build();
        Product dbProduct = productRepository.save(product);

        return new ResponseEntity<Product>(dbProduct, HttpStatus.OK);
    }

    @GetMapping(value = "/getProducts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProducts() {

        List<Product> productList = new ArrayList<>();
        productList.addAll(productRepository.findAll());
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {

        productRepository.deleteById(id);
        return new ResponseEntity<String>(String.format("Product with id %d is successfully deleted", id), HttpStatus.OK);
    }

}
