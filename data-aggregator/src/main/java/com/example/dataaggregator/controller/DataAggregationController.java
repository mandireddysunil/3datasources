package com.example.dataaggregator.controller;

import com.example.dataaggregator.service.DataOrchestrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1") // Base path for the controller
public class DataAggregationController {

    private static final Logger logger = LoggerFactory.getLogger(DataAggregationController.class);
    private final DataOrchestrationService dataOrchestrationService;

    @Autowired
    public DataAggregationController(DataOrchestrationService dataOrchestrationService) {
        this.dataOrchestrationService = dataOrchestrationService;
    }

    @GetMapping("/trigger-aggregation")
    public ResponseEntity<String> triggerAggregation() {
        logger.info("Received request to trigger data aggregation via controller.");
        try {
            dataOrchestrationService.orchestrateDataAggregation();
            String message = "Data aggregation process triggered and completed successfully.";
            logger.info(message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            String errorMessage = "Error during data aggregation process: " + e.getMessage();
            logger.error(errorMessage, e);
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }
}
