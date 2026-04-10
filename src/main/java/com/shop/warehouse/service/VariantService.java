package com.shop.warehouse.service;

import com.shop.warehouse.model.Product;
import com.shop.warehouse.model.Variant;
import com.shop.warehouse.repository.ProductRepository;
import com.shop.warehouse.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VariantService {

    @Autowired
    private VariantRepository variantRepository;
    
    @Autowired
    private ProductRepository productRepository;

    public List<Variant> getAllVariants() {
        return variantRepository.findAll();
    }
    
    public List<Variant> getVariantsByProduct(Long productId) {
        return variantRepository.findByProductId(productId);
    }

    public Optional<Variant> getVariantById(Long id) {
        return variantRepository.findById(id);
    }

    public Variant createVariant(Long productId, Variant variant) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        variant.setProduct(product);
        return variantRepository.save(variant);
    }

    public Variant updateVariant(Long id, Variant variantDetails) {
        return variantRepository.findById(id).map(variant -> {
            variant.setName(variantDetails.getName());
            variant.setPrice(variantDetails.getPrice());
            variant.setSku(variantDetails.getSku());
            variant.setQuantity(variantDetails.getQuantity());
            return variantRepository.save(variant);
        }).orElseThrow(() -> new RuntimeException("Variant not found with id " + id));
    }

    public void deleteVariant(Long id) {
        variantRepository.deleteById(id);
    }

    public Variant updateStock(Long id, Integer quantityChange) {
        return variantRepository.findById(id).map(variant -> {
            int newQuantity = variant.getQuantity() + quantityChange;
            if (newQuantity < 0) {
                throw new RuntimeException("Insufficient stock for variant id " + id);
            }
            variant.setQuantity(newQuantity);
            return variantRepository.save(variant);
        }).orElseThrow(() -> new RuntimeException("Variant not found with id " + id));
    }
}
