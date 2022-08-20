package com.github.andrielson.spring.webflux.product.service;

import com.github.andrielson.spring.webflux.product.dto.ProductDto;
import com.github.andrielson.spring.webflux.product.repository.ProductRepository;
import com.github.andrielson.spring.webflux.product.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Sinks.Many<ProductDto> sink;

    public Flux<ProductDto> getAll() {
        return this.productRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Flux<ProductDto> getProductsByPriceRange(int min, int max) {
        return this.productRepository
                .findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return this.productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return this.productRepository
                .findById(id)
                .flatMap(product -> productDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(it -> it.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.productRepository.deleteById(id);
    }
}
