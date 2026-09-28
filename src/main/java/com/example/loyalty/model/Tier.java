package com.example.loyalty.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Tier entity defining loyalty tier thresholds and discounts")
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the tier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Name of the tier (e.g. Gold, Silver, Bronze)", example = "Gold")
    private String tierName;

    @Schema(description = "Minimum points required to reach this tier", example = "1000")
    private int minimumPoints;

    @Schema(description = "Discount percentage associated with this tier", example = "15.0")
    private double discountPercentage;
}
