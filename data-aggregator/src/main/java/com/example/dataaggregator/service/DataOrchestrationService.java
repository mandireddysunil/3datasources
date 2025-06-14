package com.example.dataaggregator.service;

import com.example.dataaggregator.model.rest.RestApiResponseData;
import com.example.dataaggregator.model.sdk.MockSdkData;
import com.example.dataaggregator.model.source.SourceData;
import com.example.dataaggregator.model.target.TargetData;
import com.example.dataaggregator.repository.target.TargetDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For target DB operations

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // For handling optional numeric values

@Service
public class DataOrchestrationService {

    private static final Logger logger = LoggerFactory.getLogger(DataOrchestrationService.class);

    private final SourceDbService sourceDbService;
    private final RestApiService restApiService;
    private final SdkDataService sdkDataService;
    private final TargetDataRepository targetDataRepository;

    @Autowired
    public DataOrchestrationService(SourceDbService sourceDbService,
                                    RestApiService restApiService,
                                    SdkDataService sdkDataService,
                                    TargetDataRepository targetDataRepository) {
        this.sourceDbService = sourceDbService;
        this.restApiService = restApiService;
        this.sdkDataService = sdkDataService;
        this.targetDataRepository = targetDataRepository;
    }

    // Use the transaction manager associated with the targetEntityManagerFactory
    @Transactional("targetTransactionManager")
    public void orchestrateDataAggregation() {
        logger.info("Starting data aggregation orchestration...");
        List<TargetData> allTargetData = new ArrayList<>();
        Instant aggregationTime = Instant.now();

        // 1. Fetch from Source PostgreSQL DB
        try {
            List<SourceData> sourceDbItems = sourceDbService.getAllSourceData();
            logger.info("Fetched {} items from Source DB.", sourceDbItems.size());
            for (SourceData item : sourceDbItems) {
                TargetData td = new TargetData();
                td.setOriginalSourceId(String.valueOf(item.getId()));
                td.setSourceType("POSTGRESQL_SOURCE");
                td.setPrimaryTextData(item.getName());
                td.setSecondaryTextData(item.getValue());
                // td.setNumericValue(null); // No direct numeric value in SourceData
                // td.setOriginalTimestamp(null); // No direct timestamp in SourceData
                td.setAggregationTimestamp(aggregationTime);
                allTargetData.add(td);
            }
        } catch (Exception e) {
            logger.error("Error fetching or processing data from Source DB", e);
        }

        // 2. Fetch from REST API (JSONPlaceholder Users)
        try {
            List<RestApiResponseData> apiUsers = restApiService.fetchUsersFromApi();
            logger.info("Fetched {} users from REST API.", apiUsers.size());
            for (RestApiResponseData user : apiUsers) {
                TargetData td = new TargetData();
                td.setOriginalSourceId(String.valueOf(user.getId()));
                td.setSourceType("REST_API_USER");
                td.setPrimaryTextData(user.getName());
                td.setSecondaryTextData("Username: " + user.getUsername() + ", Email: " + user.getEmail());
                // Optional: Could parse geo lat/lng if needed, or some other numeric field if available
                // td.setNumericValue(null);
                // td.setOriginalTimestamp(null);
                td.setAggregationTimestamp(aggregationTime);
                allTargetData.add(td);
            }
        } catch (Exception e) {
            logger.error("Error fetching or processing data from REST API", e);
        }

        // 3. Fetch from SDK (Mocked Data)
        try {
            List<MockSdkData> sdkItems = sdkDataService.fetchDataFromSdk();
            logger.info("Fetched {} items from Mock SDK.", sdkItems.size());
            for (MockSdkData item : sdkItems) {
                TargetData td = new TargetData();
                td.setOriginalSourceId(item.getDataId().toString());
                td.setSourceType("MOCK_SDK_METRIC");
                td.setPrimaryTextData(item.getMetricName());
                td.setSecondaryTextData("Source System: " + item.getSourceSystem());
                td.setNumericValue(item.getMetricValue());
                td.setOriginalTimestamp(item.getTimestamp());
                td.setAggregationTimestamp(aggregationTime);
                allTargetData.add(td);
            }
        } catch (Exception e) {
            logger.error("Error fetching or processing data from Mock SDK", e);
        }

        // 4. Save all aggregated data to Target DB (H2)
        if (!allTargetData.isEmpty()) {
            logger.info("Saving {} aggregated items to the target database.", allTargetData.size());
            targetDataRepository.saveAll(allTargetData);
            logger.info("Successfully saved aggregated data.");
        } else {
            logger.info("No data to save to the target database.");
        }
    }
}
