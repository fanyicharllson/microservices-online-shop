package org.charllson.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.charllson.productservice.dto.ProductRequest;
import org.charllson.productservice.dto.ProductResponse;
import org.charllson.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
     private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct() {
       return productService.getAllProduct();
    }
}
