package com.example.loyalty.service;

import com.example.loyalty.model.Customer;
import com.example.loyalty.model.PointsLedger;
import com.example.loyalty.repository.CustomerRepository;
import com.example.loyalty.repository.PointsLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsLedgerService {

    @Autowired
    private PointsLedgerRepository pointsLedgerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    public PointsLedger create(PointsLedger ledger) {
        Customer customer = customerRepository.findById(ledger.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if ("REDEEM".equalsIgnoreCase(ledger.getTransactionType())) {
            if (ledger.getPoints() > customer.getAvailablePoints()) {
                throw new RuntimeException("Insufficient points");
            }
            customer.setAvailablePoints(customer.getAvailablePoints() - ledger.getPoints());
            customerRepository.save(customer);
        } else if ("EARN".equalsIgnoreCase(ledger.getTransactionType())) {
            customer.setTotalPoints(customer.getTotalPoints() + ledger.getPoints());
            customer.setAvailablePoints(customer.getAvailablePoints() + ledger.getPoints());
            customerService.updateCustomerTier(customer);
            customerRepository.save(customer);
        }

        return pointsLedgerRepository.save(ledger);
    }

    public List<PointsLedger> getAll() {
        return pointsLedgerRepository.findAll();
    }

    public PointsLedger getById(Integer id) {
        return pointsLedgerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ledger entry not found"));
    }

    public PointsLedger updateById(Integer id, PointsLedger ledgerDetails) {
        PointsLedger existingLedger = getById(id);
        existingLedger.setCustomerId(ledgerDetails.getCustomerId());
        existingLedger.setPoints(ledgerDetails.getPoints());
        existingLedger.setTransactionType(ledgerDetails.getTransactionType());
        existingLedger.setDescription(ledgerDetails.getDescription());
        return pointsLedgerRepository.save(existingLedger);
    }

    public void deleteById(Integer id) {
        PointsLedger existingLedger = getById(id);
        pointsLedgerRepository.deleteById(existingLedger.getId());
    }
}
