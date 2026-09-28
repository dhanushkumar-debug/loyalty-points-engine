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
@Schema(description = "Purchase transaction record")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the purchase record", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "ID of the customer who made the purchase", example = "1")
    private Integer customerId;

    @Schema(description = "Monetary amount of the purchase", example = "150.75")
    private double purchaseAmount;

    @Schema(description = "Loyalty points earned from this purchase", example = "150")
    private int pointsEarned;
}
