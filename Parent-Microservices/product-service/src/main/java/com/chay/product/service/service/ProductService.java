package com.chay.product.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chay.product.service.Dto.ProductRequest;
import com.chay.product.service.Dto.ProductResponse;
import com.chay.product.service.model.Product;
import com.chay.product.service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository Repository;

    public void createProduct(ProductRequest Request) {

        Product product = Product.builder()
                .name(Request.getName())
                .description(Request.getDescription())
                .price(Request.getPrice())
                .build();

        Repository.save(product);

//		Log Message
        log.info("Product {} is Saved " , product.getId());
    }

    public List<ProductResponse> getAllProducts(){

        List<Product> products = Repository.findAll();

        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
