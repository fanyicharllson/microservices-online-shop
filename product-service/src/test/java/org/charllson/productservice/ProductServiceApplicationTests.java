package org.charllson.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.charllson.productservice.dto.ProductRequest;
import org.charllson.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.3");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper ObjectMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString)).andExpect(status().isCreated());

        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void shouldFailToCreateProductWithInvalidData() throws Exception {
        ProductRequest invalideProductRequest = ProductRequest.builder()
                .name("") // invalid pruduct name
                .description("Techno Phone for all users")
                .price("100.00")
                .build();

        // convert object to string        
        String invalidProductReqString = objectMapper.writeValueAsString(invalideProductRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
                .content(invalidProductReqString)).andExpect(status().isBadRequest());
    }

    // Helper  methods
     private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Techno Spark 10")
                .description("Techno Phone for all users")
                .price(String.valueOf(BigDecimal.valueOf(12000)))
                .build();
    }

    @SuppressWarnings("unused")
    private ProductRequest creaProductRequest(String name, String description, String price) {
        return ProductRequest.builder()
        .name(name)
        .description(description)
        .price(price)
        .build();
    }
}
