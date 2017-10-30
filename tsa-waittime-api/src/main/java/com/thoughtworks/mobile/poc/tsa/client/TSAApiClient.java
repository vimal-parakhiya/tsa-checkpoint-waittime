package com.thoughtworks.mobile.poc.tsa.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.mobile.poc.tsa.repository.TSAAirportRepository;
import com.thoughtworks.mobile.poc.tsa.client.response.TSAAirport;
import com.thoughtworks.mobile.poc.tsa.client.response.TSAWaitTimeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class TSAApiClient {
    private final static Logger logger = LoggerFactory.getLogger(TSAApiClient.class);

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;
    private TSAAirportRepository airportRepository;

    @Autowired
    public TSAApiClient(ObjectMapper objectMapper, RestTemplate restTemplate, TSAAirportRepository airportRepository) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.airportRepository = airportRepository;
    }

    public TSAWaitTimeList fetchSecurityCheckpointWaitTimes(String airportCode) {
        Optional<TSAAirport> tsaAirport = airportRepository.getAirport(airportCode);
        return tsaAirport.map(airport -> loadWaitTimes(airportCode)).orElse(new TSAWaitTimeList());
    }

    private TSAWaitTimeList loadWaitTimes(String airportCode) {
        try {
            String waitTimeUrl = "https://apps.tsa.dhs.gov/MyTSAWebService/GetTSOWaitTimes.ashx?ap=%s&output=json";
            ResponseEntity<String> response = restTemplate.getForEntity(String.format(waitTimeUrl, airportCode), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readValue(response.getBody(), TSAWaitTimeList.class);
            }
        } catch (Exception ex) {
            logger.error("Failed to load security wait-time for airport: {}", airportCode, ex);
        }
        return new TSAWaitTimeList();
    }
}
