package com.demo.file.demofileservices.controller;

import com.demo.file.demofileservices.config.FileConfig;
import com.demo.file.demofileservices.dao.ProductRepository;
import com.demo.file.demofileservices.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Successful");
    }

    @PostMapping(value = "/insertProduct", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> createStudent(@RequestParam MultipartFile file, @RequestParam String name,
                                                 @RequestParam String description, @RequestParam String price) throws IOException {

        Files.copy(file.getInputStream(), FileConfig.getRootPath().resolve(file.getOriginalFilename()));
        List<String> fileInfos = loadAll().map(path -> {
            //TODO Learn: MvcUriComponentsBuilder to build url
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ProductController.class, "getFile",
                            path.getFileName().toString()).build().toString();
//            String url = String.format("http://localhost:9091/uploads/images/%s", path.getFileName());
            return url;
        }).collect(Collectors.toList());
        Product product = Product.builder().name(name)
                .description(description).price(price)
                .fileName(file.getOriginalFilename())
                .file_path(fileInfos.get(fileInfos.size() - 1))
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

    @GetMapping(value = "/getProductById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {

        return new ResponseEntity<>(productRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/getProductByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProductById(@PathVariable String name) {

        return new ResponseEntity<>(productRepository.findByName(name), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {

        productRepository.deleteById(id);
        return new ResponseEntity<String>(String.format("Product with id %d is successfully deleted", id), HttpStatus.OK);
    }

   /* @GetMapping("/uploads/images/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Path file = FileConfig.getRootPath().resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }*/

    public Stream<Path> loadAll() {
        try {
            return Files.walk(FileConfig.getRootPath(), 1).filter(path -> !path.equals(FileConfig.getRootPath())).map(FileConfig.getRootPath()::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @PostMapping(value = "/updateProductById/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> updateStudent(@RequestParam(required = false) MultipartFile file,
                                                 @RequestParam(required = false) String file_path, @PathVariable Integer id, @RequestParam String name,
                                                 @RequestParam String description, @RequestParam String price) throws IOException {

        List<String> fileInfos;
        StringBuilder filePath = new StringBuilder(file_path);
        String[] array = file_path.split("/");
        StringBuilder originalFileName = new StringBuilder(array[array.length - 1]);
        if (file != null) {
            Files.copy(file.getInputStream(), FileConfig.getRootPath().resolve(file.getOriginalFilename()));
            fileInfos = loadAll().map(path -> {
                String url = String.format("http://localhost:9091/uploads/images/%s", path.getFileName());
                return url;
            }).collect(Collectors.toList());
            filePath.replace(0, filePath.length(), fileInfos.get(fileInfos.size() - 1));
            originalFileName.replace(0, originalFileName.length(), file.getOriginalFilename());
        }

        Product product = Product.builder().id(id).name(name)
                .description(description).price(price)
                .fileName(originalFileName.toString())
                .file_path(filePath.toString())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString()).build();
        Product dbProduct = productRepository.save(product);

        return new ResponseEntity<Product>(dbProduct, HttpStatus.OK);
    }


    //TODO: call this method while forming file location url
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Path file = FileConfig.getRootPath().resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

}
