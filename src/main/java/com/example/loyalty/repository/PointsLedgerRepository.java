package com.example.loyalty.repository;

import com.example.loyalty.model.PointsLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointsLedgerRepository extends JpaRepository<PointsLedger, Integer> {
}
