package com.example.product.command.aggregate;

import com.example.product.command.commands.AddProductCommand;
import com.example.product.command.events.ProductAddedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;


    public ProductAggregate() {
    }


    @CommandHandler
    public ProductAggregate(AddProductCommand createProductCommand) {

        //You can perform all the validations here

        ProductAddedEvent productCreatedEvent =
                new ProductAddedEvent();

        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);  //publish the event
    }


    @EventSourcingHandler
    public void on(ProductAddedEvent productCreatedEvent) {
        this.quantity = productCreatedEvent.getQuantity();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.name = productCreatedEvent.getName();
    }
}
