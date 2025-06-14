package com.example.dataaggregator.service;

import com.example.dataaggregator.model.source.SourceData;
import com.example.dataaggregator.repository.source.SourceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceDbService {

    private final SourceDataRepository sourceDataRepository;

    @Autowired
    public SourceDbService(SourceDataRepository sourceDataRepository) {
        this.sourceDataRepository = sourceDataRepository;
    }

    public List<SourceData> getAllSourceData() {
        // This method retrieves all data from the SourceData table.
        // You might want to add methods with specific queries or pagination.
        return sourceDataRepository.findAll();
    }

    // You can add more methods here to fetch data based on specific criteria.
}
