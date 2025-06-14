package com.example.dataaggregator.repository.target;

import com.example.dataaggregator.model.target.TargetData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetDataRepository extends JpaRepository<TargetData, Long> {
    // Custom query methods for TargetData can be added here if needed
}
