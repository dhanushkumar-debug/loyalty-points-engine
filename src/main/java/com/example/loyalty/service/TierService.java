package com.example.loyalty.service;

import com.example.loyalty.model.Tier;
import com.example.loyalty.repository.TierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TierService {

    @Autowired
    private TierRepository tierRepository;

    public Tier create(Tier tier) {
        return tierRepository.save(tier);
    }

    public List<Tier> getAll() {
        return tierRepository.findAll();
    }

    public Tier getById(Integer id) {
        return tierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tier not found"));
    }

    public Tier updateById(Integer id, Tier tierDetails) {
        Tier existingTier = getById(id);
        existingTier.setTierName(tierDetails.getTierName());
        existingTier.setMinimumPoints(tierDetails.getMinimumPoints());
        existingTier.setDiscountPercentage(tierDetails.getDiscountPercentage());
        return tierRepository.save(existingTier);
    }

    public void deleteById(Integer id) {
        Tier existingTier = getById(id);
        tierRepository.deleteById(existingTier.getId());
    }
}
