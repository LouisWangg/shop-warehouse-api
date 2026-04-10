package com.shop.warehouse.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "variants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    @NotBlank(message = "Variant name is mandatory")
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    @PositiveOrZero(message = "Price must be greater than or equal to zero")
    private BigDecimal price;

    @Column(nullable = false)
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> attributes;

}
