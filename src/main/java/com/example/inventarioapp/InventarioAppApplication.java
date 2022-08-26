package com.example.inventarioapp;

import com.example.inventarioapp.Controller.ProductController;
import com.example.inventarioapp.Model.Product;
import com.example.inventarioapp.Model.ProductRepository;
import com.example.inventarioapp.View.ProductView;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class InventarioAppApplication {

    @Autowired
    ProductRepository productRepository;
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(InventarioAppApplication.class).headless(false).run(args);

    }

    @Bean
    void applicationRunner(){
        ProductController control = new ProductController(productRepository, new ProductView());
        
        
    }
    
}
