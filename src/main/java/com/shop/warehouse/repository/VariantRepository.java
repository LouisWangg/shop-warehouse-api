package com.shop.warehouse.repository;

import com.shop.warehouse.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    Optional<Variant> findBySku(String sku);
    List<Variant> findByProductId(Long productId);
}
