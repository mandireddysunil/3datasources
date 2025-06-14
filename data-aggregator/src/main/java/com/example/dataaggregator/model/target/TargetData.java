package com.example.dataaggregator.model.target;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column; // Added for clarity and potential length constraints
import lombok.Data;
import lombok.NoArgsConstructor; // Added for completeness
import lombok.AllArgsConstructor; // Added for completeness

import java.time.Instant; // Changed from LocalDateTime for consistency with MockSdkData

@Entity
@Table(name = "aggregated_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalId; // Renamed from 'id' for clarity

    @Column(length = 100) // Example length constraint
    private String originalSourceId; // To store ID from source (String to accommodate UUIDs/numbers)

    @Column(length = 50)
    private String sourceType; // E.g., "POSTGRESQL", "REST_API_USER", "MOCK_SDK_METRIC"

    @Column(length = 255)
    private String primaryTextData; // For names, titles, usernames, metric names

    @Column(length = 1000) // Potentially longer text
    private String secondaryTextData; // For values, emails, catchphrases, etc.

    private Double numericValue; // For any numeric data like metricValue

    private Instant originalTimestamp; // If source provides a timestamp (like MockSdkData)

    private Instant aggregationTimestamp; // When this record was created in the target DB

    // Constructors, getters, setters, etc., handled by Lombok
}
