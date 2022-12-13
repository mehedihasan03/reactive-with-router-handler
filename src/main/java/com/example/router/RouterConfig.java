package com.example.router;

import com.example.handler.ProductHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired(required = true)
    private ProductHandler handler;


    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/products", handler::getProducts)
                .GET("/router/product/{id}", handler::getProduct)
                .POST("/router/product", handler::saveProducts)
                .PUT("/router/product/update/{id}", handler::updateProducts)
                .DELETE("/router/product/delete/{id}", handler::deleteProducts)
                .GET("/router/products/range", handler::getInRange)
                .build();
    }
}
