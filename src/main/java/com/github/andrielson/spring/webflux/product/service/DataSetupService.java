package com.github.andrielson.spring.webflux.product.service;

import com.github.andrielson.spring.webflux.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DataSetupService implements CommandLineRunner {
    private final ProductService productService;

    @Override
    public void run(String... args) {
        ProductDto p1 = new ProductDto("4k-tv", 1000);
        ProductDto p2 = new ProductDto("slr-camera", 750);
        ProductDto p3 = new ProductDto("iphone", 800);
        ProductDto p4 = new ProductDto("headphone", 100);

        Flux.just(p1, p2, p3, p4)
                .flatMap(p -> this.productService.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }
}
