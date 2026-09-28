package com.example.loyalty.controller;

import com.example.loyalty.model.Customer;
import com.example.loyalty.service.CustomerService;
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
@RequestMapping("/customers")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@Tag(name = "Customer Controller", description = "Endpoints for managing customer profiles and loyalty points")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    @Operation(summary = "Create a new customer", description = "Registers a new customer in the loyalty system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully",
                    content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer details or duplicate entry",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        try {
            Customer created = customerService.create(customer);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all customers", description = "Retrieves a list of all registered customers.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/getbyid/{id}")
    @Operation(summary = "Get customer by ID", description = "Retrieves details of a customer by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> getById(@Parameter(description = "ID of the customer to fetch", required = true) @PathVariable Integer id) {
        try {
            Customer customer = customerService.getById(id);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update customer by ID", description = "Updates details of an existing customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = @Content(schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the customer to update", required = true) @PathVariable Integer id,
            @RequestBody Customer customerDetails) {
        try {
            Customer updated = customerService.updateById(id, customerDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete customer by ID", description = "Deletes a customer profile from the system by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<?> delete(@Parameter(description = "ID of the customer to delete", required = true) @PathVariable Integer id) {
        try {
            customerService.deleteById(id);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
