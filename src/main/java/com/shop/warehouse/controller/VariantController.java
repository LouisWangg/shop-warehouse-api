package com.shop.warehouse.controller;

import com.shop.warehouse.model.Variant;
import com.shop.warehouse.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
public class VariantController {

    @Autowired
    private VariantService variantService;

    @GetMapping
    public List<Variant> getAllVariants() {
        return variantService.getAllVariants();
    }
    
    @GetMapping("/product/{productId}")
    public List<Variant> getVariantsByProduct(@PathVariable Long productId) {
        return variantService.getVariantsByProduct(productId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variant> getVariantById(@PathVariable Long id) {
        return variantService.getVariantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> createVariant(@PathVariable Long productId, @RequestBody Variant variant) {
        try {
            return ResponseEntity.ok(variantService.createVariant(productId, variant));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variant> updateVariant(@PathVariable Long id, @RequestBody Variant variant) {
        try {
            Variant updatedVariant = variantService.updateVariant(id, variant);
            return ResponseEntity.ok(updatedVariant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        variantService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam Integer quantityChange) {
        try {
            Variant updatedVariant = variantService.updateStock(id, quantityChange);
            return ResponseEntity.ok(updatedVariant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
