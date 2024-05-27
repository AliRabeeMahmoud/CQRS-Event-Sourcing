package com.example.product.query.projection;


import com.example.product.command.data.Product;
import com.example.product.command.data.ProductRepository;
import com.example.product.command.model.AddProductCommandDto;
import com.example.product.query.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<AddProductCommandDto> handle(GetProductsQuery getProductsQuery) {
        List<Product> products =
                productRepository.findAll();

        List<AddProductCommandDto> productRestModels =
                products.stream()
                        .map(product -> AddProductCommandDto
                                .builder()
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .name(product.getName())
                                .build())
                        .collect(Collectors.toList());

        return productRestModels;
    }
}
