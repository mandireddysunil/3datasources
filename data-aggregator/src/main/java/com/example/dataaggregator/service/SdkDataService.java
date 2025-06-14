package com.example.dataaggregator.service;

import com.example.dataaggregator.model.sdk.MockSdkData; // New import
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant; // New import
import java.util.ArrayList; // New import
import java.util.List; // New import
import java.util.UUID; // New import
import java.util.Random; // New import

@Service
public class SdkDataService {

    private static final Logger logger = LoggerFactory.getLogger(SdkDataService.class);
    private final Random random = new Random();

    public SdkDataService() {
        logger.info("SdkDataService initialized (using mock data generation).");
    }

    public List<MockSdkData> fetchDataFromSdk() {
        logger.info("Generating mock data as if fetched from an SDK...");
        List<MockSdkData> mockDataList = new ArrayList<>();

        int numberOfRecords = random.nextInt(5) + 1; // Generate 1 to 5 records

        for (int i = 0; i < numberOfRecords; i++) {
            MockSdkData data = new MockSdkData(
                    UUID.randomUUID(),
                    "sample.metric." + (char) ('a' + i),
                    random.nextDouble() * 100,
                    Instant.now().minusSeconds(random.nextInt(3600)), // Timestamp within the last hour
                    "MockSDKSystem"
            );
            mockDataList.add(data);
        }

        logger.info("Successfully generated {} mock SDK data records.", mockDataList.size());
        return mockDataList;
    }
}
