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
@Schema(description = "Points ledger entry tracking points transactions (EARNED, REDEEMED, ADJUSTED)")
public class PointsLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the ledger record", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "ID of the customer associated with the points entry", example = "1")
    private Integer customerId;

    @Schema(description = "Number of points added or deducted", example = "100")
    private int points;

    @Schema(description = "Transaction type (e.g., EARNED, REDEEMED)", example = "EARNED")
    private String transactionType;

    @Schema(description = "Detailed description of the transaction", example = "Earned from purchase #102")
    private String description;
}
