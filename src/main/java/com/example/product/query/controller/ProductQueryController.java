package com.example.product.query.controller;

import com.example.product.command.dto.AddProductCommandDto;
import com.example.product.query.queries.GetProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<AddProductCommandDto> getAllProducts() {
        GetProductsQuery getProductsQuery =
                new GetProductsQuery();

        List<AddProductCommandDto> productRestModels =
        queryGateway.query(getProductsQuery,
                ResponseTypes.multipleInstancesOf(AddProductCommandDto.class))
                .join();

        return productRestModels;
    }
}
