package com.example.loyalty.controller;

import com.example.loyalty.model.Tier;
import com.example.loyalty.service.TierService;
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
@RequestMapping("/tiers")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Tag(name = "Tier Controller", description = "Endpoints for managing loyalty tiers")
public class TierController {

    @Autowired
    private TierService tierService;

    @PostMapping("/create")
    @Operation(summary = "Create a new loyalty tier", description = "Creates a new loyalty tier with minimum points and discount percentage.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tier created successfully",
                    content = @Content(schema = @Schema(implementation = Tier.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or tier creation error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> create(@RequestBody Tier tier) {
        try {
            Tier created = tierService.create(tier);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all loyalty tiers", description = "Retrieves a list of all existing loyalty tiers.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tiers")
    public ResponseEntity<List<Tier>> getAll() {
        return ResponseEntity.ok(tierService.getAll());
    }

    @GetMapping("/getbyid/{id}")
    @Operation(summary = "Get tier by ID", description = "Retrieves a specific loyalty tier by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tier found",
                    content = @Content(schema = @Schema(implementation = Tier.class))),
            @ApiResponse(responseCode = "404", description = "Tier not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> getById(@Parameter(description = "ID of the tier to fetch", required = true) @PathVariable Integer id) {
        try {
            Tier tier = tierService.getById(id);
            return ResponseEntity.ok(tier);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update tier by ID", description = "Updates details of an existing loyalty tier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tier updated successfully",
                    content = @Content(schema = @Schema(implementation = Tier.class))),
            @ApiResponse(responseCode = "404", description = "Tier not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the tier to update", required = true) @PathVariable Integer id,
            @RequestBody Tier tierDetails) {
        try {
            Tier updated = tierService.updateById(id, tierDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete tier by ID", description = "Deletes a loyalty tier by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tier deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Tier not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID of the tier to delete", required = true) @PathVariable Integer id) {
        try {
            tierService.deleteById(id);
            return ResponseEntity.ok("Tier deleted successfully");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
