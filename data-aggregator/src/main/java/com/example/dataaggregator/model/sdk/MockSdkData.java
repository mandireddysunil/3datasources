package com.example.dataaggregator.model.sdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockSdkData {
    private UUID dataId;
    private String metricName;
    private double metricValue;
    private Instant timestamp;
    private String sourceSystem;
}
