package com.example.dataaggregator.service;

import com.example.dataaggregator.model.rest.RestApiResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RestApiService {

    private static final Logger logger = LoggerFactory.getLogger(RestApiService.class);
    private final RestTemplate restTemplate;
    private static final String JSONPLACEHOLDER_USERS_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    public RestApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Updated to fetch a list of users from JSONPlaceholder
    public List<RestApiResponseData> fetchUsersFromApi() {
        logger.info("Fetching user data from API: {}", JSONPLACEHOLDER_USERS_URL);
        try {
            RestApiResponseData[] response = restTemplate.getForObject(JSONPLACEHOLDER_USERS_URL, RestApiResponseData[].class);
            if (response != null) {
                logger.info("Successfully fetched {} users from API", response.length);
                return Arrays.asList(response);
            } else {
                logger.warn("Received null response from API: {}", JSONPLACEHOLDER_USERS_URL);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Error calling REST API: {}", JSONPLACEHOLDER_USERS_URL, e);
            return Collections.emptyList();
        }
    }
}
