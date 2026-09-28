package com.example.loyalty.service;

import com.example.loyalty.model.Customer;
import com.example.loyalty.model.PointsLedger;
import com.example.loyalty.model.Purchase;
import com.example.loyalty.repository.CustomerRepository;
import com.example.loyalty.repository.PointsLedgerRepository;
import com.example.loyalty.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PointsLedgerRepository pointsLedgerRepository;

    @Autowired
    private CustomerService customerService;

    public Purchase create(Purchase purchase) {
        Customer customer = customerRepository.findById(purchase.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        int pointsEarned = (int) purchase.getPurchaseAmount();
        purchase.setPointsEarned(pointsEarned);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Update Customer Points
        customer.setTotalPoints(customer.getTotalPoints() + pointsEarned);
        customer.setAvailablePoints(customer.getAvailablePoints() + pointsEarned);

        // Automatic Tier Update
        customerService.updateCustomerTier(customer);
        customerRepository.save(customer);

        // Record in Points Ledger
        PointsLedger ledger = new PointsLedger();
        ledger.setCustomerId(customer.getId());
        ledger.setPoints(pointsEarned);
        ledger.setTransactionType("EARN");
        ledger.setDescription("Earned points from purchase ID: " + savedPurchase.getId());
        pointsLedgerRepository.save(ledger);

        return savedPurchase;
    }

    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    public Purchase getById(Integer id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public Purchase updateById(Integer id, Purchase purchaseDetails) {
        Purchase existingPurchase = getById(id);
        existingPurchase.setCustomerId(purchaseDetails.getCustomerId());
        existingPurchase.setPurchaseAmount(purchaseDetails.getPurchaseAmount());
        existingPurchase.setPointsEarned(purchaseDetails.getPointsEarned());
        return purchaseRepository.save(existingPurchase);
    }

    public void deleteById(Integer id) {
        Purchase existingPurchase = getById(id);
        purchaseRepository.deleteById(existingPurchase.getId());
    }
}
