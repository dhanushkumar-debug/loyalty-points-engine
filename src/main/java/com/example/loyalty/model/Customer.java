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
@Schema(description = "Customer entity representing a retail loyalty member")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the customer", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Full name of the customer", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the customer", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Phone number of the customer", example = "+1234567890")
    private String phone;

    @Schema(description = "Total cumulative points earned", example = "500")
    private int totalPoints = 0;

    @Schema(description = "Currently available points balance", example = "350")
    private int availablePoints = 0;

    @Schema(description = "Loyalty tier of the customer", example = "Silver")
    private String tier = "Bronze";
}
