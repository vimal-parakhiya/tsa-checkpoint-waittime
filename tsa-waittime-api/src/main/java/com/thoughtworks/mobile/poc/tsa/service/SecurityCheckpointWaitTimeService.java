package com.thoughtworks.mobile.poc.tsa.service;

import com.thoughtworks.mobile.poc.tsa.client.TSAApiClient;
import com.thoughtworks.mobile.poc.tsa.client.response.Checkpoint;
import com.thoughtworks.mobile.poc.tsa.client.response.TSAAirport;
import com.thoughtworks.mobile.poc.tsa.client.response.TSAWaitTime;
import com.thoughtworks.mobile.poc.tsa.client.response.TSAWaitTimeList;
import com.thoughtworks.mobile.poc.tsa.domain.Airport;
import com.thoughtworks.mobile.poc.tsa.domain.SecurityCheckpoint;
import com.thoughtworks.mobile.poc.tsa.repository.TSAAirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityCheckpointWaitTimeService {
    private TSAAirportRepository airportRepository;
    private TSAApiClient tsaApiClient;

    @Autowired
    public SecurityCheckpointWaitTimeService(TSAAirportRepository airportRepository, TSAApiClient tsaApiClient) {
        this.airportRepository = airportRepository;
        this.tsaApiClient = tsaApiClient;
    }

    public Optional<Airport> fetchSecurityCheckpointWaitTimes(String airportCode) {
        Optional<TSAAirport> tsaAirport = airportRepository.getAirport(airportCode);
        Airport airport = null;
        if (tsaAirport.isPresent()) {
            airport = buildAirport(tsaAirport.get());
        }
        return Optional.ofNullable(airport);
    }

    private Airport buildAirport(TSAAirport tsaAirport) {
        TSAWaitTimeList tsaWaitTimeList = tsaApiClient.fetchSecurityCheckpointWaitTimes(tsaAirport.getShortcode());
        List<SecurityCheckpoint> securityCheckpoints = buildSecurityCheckpoints(tsaAirport, tsaWaitTimeList);
        return new Airport(tsaAirport.getShortcode(), tsaAirport.getName(), securityCheckpoints);
    }

    private List<SecurityCheckpoint> buildSecurityCheckpoints(TSAAirport airport, TSAWaitTimeList tsaWaitTimeList) {
        List<SecurityCheckpoint> securityCheckpoints = new ArrayList<>();
        for (int i = 0; i < airport.getCheckpoints().size(); ++i) {
            securityCheckpoints.add(buildSecurityCheckpoint(i, airport.getCheckpoints().get(i), tsaWaitTimeList));
        }
        return securityCheckpoints;
    }

    private SecurityCheckpoint buildSecurityCheckpoint(int index, Checkpoint checkpoint, TSAWaitTimeList tsaWaitTimeList) {
        List<TSAWaitTime> tsaWaitTimes = tsaWaitTimeList.getWaitTimes(index);
        return new SecurityCheckpoint(
                checkpoint.getShortname(),
                checkpoint.getLongname(),
                checkpoint.isPrecheck(),
                tsaWaitTimes,
                checkpoint.getPrecheckCarriers()
        );
    }
}
