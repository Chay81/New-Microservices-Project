package com.chay.product.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import com.chay.product.service.Dto.ProductRequest;
import com.chay.product.service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer Container = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc Mock;
    @Autowired
    private ObjectMapper Mapper;
    @Autowired
    private ProductRepository Repository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDPR) {
        dymDPR.add("spring.data.mongodb.uri", Container::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest proreq = getProductRequest();
        String proreqString = Mapper.writeValueAsString(proreq);
        Mock.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(proreqString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, Repository.findAll().size());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Nokia")
                .description("Nokia")
                .price(BigDecimal.valueOf(8000))
                .build();
    }
}
