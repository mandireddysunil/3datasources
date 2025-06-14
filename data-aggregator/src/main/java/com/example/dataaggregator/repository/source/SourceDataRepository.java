package com.example.dataaggregator.repository.source;

import com.example.dataaggregator.model.source.SourceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceDataRepository extends JpaRepository<SourceData, Long> {
    // You can add custom query methods here if needed
}
