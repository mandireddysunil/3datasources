package com.example.dataaggregator.service;

import com.example.dataaggregator.model.rest.RestApiResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RestApiService {

    private static final Logger logger = LoggerFactory.getLogger(RestApiService.class);
    private final RestTemplate restTemplate;

    @Autowired
    public RestApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestApiResponseData[] fetchDataFromApi(String apiUrl) {
        // This is a placeholder URL. Replace with your actual API endpoint.
        // The response is expected to be an array of RestApiResponseData objects.
        // Adjust if the API returns a single object or a different structure.
        logger.info("Fetching data from API: {}", apiUrl);
        try {
            // Example: expecting a JSON array
            RestApiResponseData[] response = restTemplate.getForObject(apiUrl, RestApiResponseData[].class);
            logger.info("Successfully fetched {} records from API", response != null ? response.length : 0);
            return response;
        } catch (Exception e) {
            logger.error("Error calling REST API: {}", apiUrl, e);
            // Handle exceptions appropriately (e.g., return empty array, throw custom exception)
            return new RestApiResponseData[0];
        }
    }
}
