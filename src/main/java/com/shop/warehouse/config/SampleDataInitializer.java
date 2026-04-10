package com.shop.warehouse.config;

import com.shop.warehouse.model.Product;
import com.shop.warehouse.model.Variant;
import com.shop.warehouse.repository.ProductRepository;
import com.shop.warehouse.repository.VariantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SampleDataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository, VariantRepository variantRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                // Create sample product
                Product shirt = new Product();
                shirt.setName("Classic T-Shirt");
                shirt.setDescription("100% cotton base t-shirt");
                shirt.setCode("TSH-001");
                productRepository.save(shirt);

                // Create sample variants
                Variant redMedium = new Variant();
                redMedium.setProduct(shirt);
                redMedium.setName("Red - Medium");
                redMedium.setSku("TSH-001-R-M");
                redMedium.setPrice(new BigDecimal("19.99"));
                redMedium.setQuantity(50);
                variantRepository.save(redMedium);

                Variant blueLarge = new Variant();
                blueLarge.setProduct(shirt);
                blueLarge.setName("Blue - Large");
                blueLarge.setSku("TSH-001-B-L");
                blueLarge.setPrice(new BigDecimal("21.99"));
                blueLarge.setQuantity(30);
                variantRepository.save(blueLarge);
                
                System.out.println("Sample database data successfully initialized!");
            }
        };
    }
}
