package com.example.loyalty.service;

import com.example.loyalty.model.Customer;
import com.example.loyalty.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        if (customer.getTier() == null || customer.getTier().isEmpty()) {
            customer.setTier("Bronze");
        }
        updateCustomerTier(customer);
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer updateById(Integer id, Customer customerDetails) {
        Customer existingCustomer = getById(id);
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhone(customerDetails.getPhone());
        existingCustomer.setTotalPoints(customerDetails.getTotalPoints());
        existingCustomer.setAvailablePoints(customerDetails.getAvailablePoints());
        
        updateCustomerTier(existingCustomer);
        return customerRepository.save(existingCustomer);
    }

    public void deleteById(Integer id) {
        Customer existingCustomer = getById(id);
        customerRepository.deleteById(existingCustomer.getId());
    }

    public void updateCustomerTier(Customer customer) {
        int totalPoints = customer.getTotalPoints();
        if (totalPoints >= 500) {
            customer.setTier("Gold");
        } else if (totalPoints >= 100) {
            customer.setTier("Silver");
        } else {
            customer.setTier("Bronze");
        }
    }
}
