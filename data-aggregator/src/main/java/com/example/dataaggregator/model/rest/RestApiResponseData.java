package com.example.dataaggregator.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Important for nested objects if we don't map everything
public class RestApiResponseData {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Address address; // Nested object
    private String phone;
    private String website;
    private Company company; // Nested object

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geo {
        private String lat;
        private String lng;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }
}
