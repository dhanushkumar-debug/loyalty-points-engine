package com.example.loyalty.controller;

import com.example.loyalty.model.Purchase;
import com.example.loyalty.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Tag(name = "Purchase Controller", description = "Endpoints for recording and managing purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/create")
    @Operation(summary = "Record a purchase", description = "Records a purchase for a customer and awards points based on current tier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase recorded successfully",
                    content = @Content(schema = @Schema(implementation = Purchase.class))),
            @ApiResponse(responseCode = "400", description = "Invalid purchase details or customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> create(@RequestBody Purchase purchase) {
        try {
            Purchase created = purchaseService.create(purchase);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all purchases", description = "Retrieves a list of all purchase records.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of purchases")
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    @GetMapping("/getbyid/{id}")
    @Operation(summary = "Get purchase by ID", description = "Retrieves details of a purchase record by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase found",
                    content = @Content(schema = @Schema(implementation = Purchase.class))),
            @ApiResponse(responseCode = "404", description = "Purchase not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> getById(@Parameter(description = "ID of the purchase record to fetch", required = true) @PathVariable Integer id) {
        try {
            Purchase purchase = purchaseService.getById(id);
            return ResponseEntity.ok(purchase);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update purchase by ID", description = "Updates details of an existing purchase record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase updated successfully",
                    content = @Content(schema = @Schema(implementation = Purchase.class))),
            @ApiResponse(responseCode = "404", description = "Purchase not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the purchase record to update", required = true) @PathVariable Integer id,
            @RequestBody Purchase purchaseDetails) {
        try {
            Purchase updated = purchaseService.updateById(id, purchaseDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete purchase by ID", description = "Deletes a purchase record by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Purchase not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID of the purchase record to delete", required = true) @PathVariable Integer id) {
        try {
            purchaseService.deleteById(id);
            return ResponseEntity.ok("Purchase deleted successfully");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
