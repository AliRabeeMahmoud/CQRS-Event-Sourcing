package com.example.product.query.projection;


import com.example.product.command.events.ProductAddedEvent;
import com.example.product.query.data.Product;
import com.example.product.query.data.ProductRepository;
import com.example.product.command.dto.AddProductCommandDto;
import com.example.product.query.queries.GetProductsQuery;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
//@ProcessingGroup("product")
public class ProductProjection {

    private ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @EventHandler
    public void on(ProductAddedEvent event) {
        Product product =
                new Product();
        BeanUtils.copyProperties(event,product);
        productRepository.save(product);

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
