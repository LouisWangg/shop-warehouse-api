package com.shop.warehouse.controller;

import com.shop.warehouse.model.Variant;
import com.shop.warehouse.service.VariantService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Variant> createVariant(@PathVariable Long productId, @Valid @RequestBody Variant variant) {
        return ResponseEntity.ok(variantService.createVariant(productId, variant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variant> updateVariant(@PathVariable Long id, @Valid @RequestBody Variant variant) {
        return ResponseEntity.ok(variantService.updateVariant(id, variant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        variantService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Variant> updateStock(@PathVariable Long id, @RequestParam Integer quantityChange) {
        return ResponseEntity.ok(variantService.updateStock(id, quantityChange));
    }
}
