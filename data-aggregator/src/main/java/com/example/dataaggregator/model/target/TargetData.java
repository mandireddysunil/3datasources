package com.example.dataaggregator.model.target;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "aggregated_data") // Example table name
@Data // Lombok annotation
public class TargetData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Example fields - these should represent the combined/transformed data
    private String sourceIdentifier; // e.g., an ID from the source system
    private String dataType; // e.g., "DB", "API", "SDK"
    private String processedValue;
    private java.time.LocalDateTime aggregationTimestamp;

    // Add other fields as necessary based on the data you are aggregating
}
