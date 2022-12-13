package com.example.handler;

import com.example.dto.ProductDto;
import com.example.entity.Product;
import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ProductHandler {


    @Autowired
    private ProductService service;


    public Mono<ServerResponse> getProducts(ServerRequest request) {
        Flux<ProductDto> products = service.getProducts();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(products, Product.class).log();
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ProductDto> product = service.getProduct(id).log();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(product, Product.class);
    }

    public Mono<ServerResponse> saveProducts(ServerRequest request) {
        Mono<ProductDto> productDtoMono = service.saveProduct(request.bodyToMono(ProductDto.class));
        return ServerResponse.ok()
                .body(productDtoMono, Product.class).log();
    }

    public Mono<ServerResponse> updateProducts(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<ProductDto> productDtoMono = request.bodyToMono(ProductDto.class);
        Mono<ProductDto> updateResponse = service.updateProduct(productDtoMono, id);
        return ServerResponse.ok()
                .body(updateResponse, Product.class);
    }

    public Mono<ServerResponse> deleteProducts(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> deleteResponse = service.deleteProduct(id);
        return ServerResponse.ok()
                .body(deleteResponse, Product.class);
    }

    public Mono<ServerResponse> getInRange(ServerRequest request){
        double min = Double.parseDouble(request.queryParam("min").get());
        double max = Double.parseDouble(request.queryParam("max").get());

        Flux<ProductDto> productInRange = service.getProductInPriceRange(min, max);

        return ServerResponse.ok()
                .body(productInRange, ProductDto.class);
    }


}
