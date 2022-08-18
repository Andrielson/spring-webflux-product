package com.github.andrielson.spring.webflux.product.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {
    private String id;
    private String description;
    private Integer price;
}
