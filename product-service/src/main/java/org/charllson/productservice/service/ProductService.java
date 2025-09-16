package org.charllson.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.charllson.productservice.dto.ProductRequest;
import org.charllson.productservice.dto.ProductResponse;
import org.charllson.productservice.model.Product;
import org.charllson.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {


        Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepository.save(product);
        log.info("Product {} saved!", product.getId());
        log.info("Just testing...");
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().Id(product.getId())
                .name(product.getName()).description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
