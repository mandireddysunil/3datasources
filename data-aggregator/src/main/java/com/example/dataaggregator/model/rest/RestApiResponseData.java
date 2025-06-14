package com.example.dataaggregator.model.rest;

import lombok.Data;

@Data // Lombok annotation
public class RestApiResponseData {
    // Example fields - modify according to the actual API response
    private Long id;
    private String content;
    private double numericValue;
}
