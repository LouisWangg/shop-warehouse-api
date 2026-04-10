package com.shop.warehouse.service;

import com.shop.warehouse.model.Product;
import com.shop.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        product.setCode(generateProductCode(product.getName()));
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String generateProductCode(String productName) {
        if (productName == null) productName = "";
        
        String cleansedName = productName.replaceAll("[^A-Za-z ]", "");
        String[] words = cleansedName.split(" ");
        StringBuilder prefixBuilder = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                prefixBuilder.append(word.charAt(0));
            }
        }

        String prefix = prefixBuilder.toString().toUpperCase();
        if (prefix.isEmpty()) {
            prefix = "PRD"; 
        }

        return prefix;
    }
}
