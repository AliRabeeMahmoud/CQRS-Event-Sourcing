package com.example.product.command.events;

import com.example.product.command.data.Product;
import com.example.product.command.data.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product")
public class ProductEventsHandler {

    private ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductAddedEvent event) throws Exception {
        Product product =
                new Product();
        BeanUtils.copyProperties(event,product);
        productRepository.save(product);
        throw new Exception("Exception Occurred");
    }

//    @ExceptionHandler
//    public void handle(Exception exception) throws Exception {
//        throw exception;
//    }
}
