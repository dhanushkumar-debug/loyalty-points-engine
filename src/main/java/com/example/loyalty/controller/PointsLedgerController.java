package com.example.loyalty.controller;

import com.example.loyalty.model.PointsLedger;
import com.example.loyalty.service.PointsLedgerService;
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
@RequestMapping("/ledger")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Tag(name = "Points Ledger Controller", description = "Endpoints for managing points transaction logs (earning, redeeming, adjustments)")
public class PointsLedgerController {

    @Autowired
    private PointsLedgerService pointsLedgerService;

    @PostMapping("/create")
    @Operation(summary = "Create a points ledger entry", description = "Adds a new transaction entry into the points ledger.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ledger entry created successfully",
                    content = @Content(schema = @Schema(implementation = PointsLedger.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ledger transaction details",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> create(@RequestBody PointsLedger ledger) {
        try {
            PointsLedger created = pointsLedgerService.create(ledger);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all points ledger entries", description = "Retrieves a complete log of all points ledger transactions.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved ledger transactions")
    public ResponseEntity<List<PointsLedger>> getAll() {
        return ResponseEntity.ok(pointsLedgerService.getAll());
    }

    @GetMapping("/getbyid/{id}")
    @Operation(summary = "Get ledger entry by ID", description = "Retrieves details of a specific points ledger entry by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ledger entry found",
                    content = @Content(schema = @Schema(implementation = PointsLedger.class))),
            @ApiResponse(responseCode = "404", description = "Ledger entry not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> getById(@Parameter(description = "ID of the ledger entry to fetch", required = true) @PathVariable Integer id) {
        try {
            PointsLedger ledger = pointsLedgerService.getById(id);
            return ResponseEntity.ok(ledger);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update ledger entry by ID", description = "Updates details of an existing points ledger entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ledger entry updated successfully",
                    content = @Content(schema = @Schema(implementation = PointsLedger.class))),
            @ApiResponse(responseCode = "404", description = "Ledger entry not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the ledger entry to update", required = true) @PathVariable Integer id,
            @RequestBody PointsLedger ledgerDetails) {
        try {
            PointsLedger updated = pointsLedgerService.updateById(id, ledgerDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete ledger entry by ID", description = "Deletes a points ledger entry by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ledger entry deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Ledger entry not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID of the ledger entry to delete", required = true) @PathVariable Integer id) {
        try {
            pointsLedgerService.deleteById(id);
            return ResponseEntity.ok("Ledger entry deleted successfully");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
