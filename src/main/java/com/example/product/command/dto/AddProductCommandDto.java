package com.example.product.command.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class AddProductCommandDto {

    private String name;
    private BigDecimal price;
    private Integer quantity;
}
