package com.shop.warehouse.config;

import com.shop.warehouse.model.Product;
import com.shop.warehouse.model.Variant;
import com.shop.warehouse.repository.ProductRepository;
import com.shop.warehouse.repository.VariantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;

import com.shop.warehouse.service.ProductService;
import com.shop.warehouse.service.VariantService;

@Configuration
public class SampleDataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository, VariantRepository variantRepository, ProductService productService, VariantService variantService) {
        return args -> {
            if (productRepository.count() == 0) {
                // Create sample product
                Product shirt = new Product();
                shirt.setName("Classic T-Shirt");
                shirt.setDescription("100% cotton base t-shirt");
                Product savedShirt = productService.createProduct(shirt);

                // Create sample variants
                Variant redMedium = new Variant();
                redMedium.setProduct(savedShirt);
                redMedium.setName("Red - Medium");
                redMedium.setPrice(new BigDecimal("199000"));
                redMedium.setQuantity(50);
                redMedium.setAttributes(Map.of("color", "Red", "size", "M"));
                variantService.createVariant(savedShirt.getId(), redMedium);

                Variant blueLarge = new Variant();
                blueLarge.setProduct(savedShirt);
                blueLarge.setName("Blue - Large");
                blueLarge.setPrice(new BigDecimal("219000"));
                blueLarge.setQuantity(30);
                blueLarge.setAttributes(Map.of("color", "Blue", "size", "L"));
                variantService.createVariant(savedShirt.getId(), blueLarge);
                
                System.out.println("Sample database data successfully initialized!");
            }
        };
    }
}
