package com.github.andrielson.spring.webflux.product.util;

import com.github.andrielson.spring.webflux.product.dto.ProductDto;
import com.github.andrielson.spring.webflux.product.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {
    public static ProductDto toDto(Product product) {
        var dto = new ProductDto();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    public static Product toEntity(ProductDto productDto) {
        var product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
