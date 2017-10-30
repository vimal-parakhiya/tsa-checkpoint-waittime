package com.thoughtworks.mobile.poc.tsa.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.mobile.poc.tsa.client.response.TSAAirport;
import com.thoughtworks.mobile.poc.tsa.client.response.AirportList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TSAAirportRepository {
    private final static Logger logger = LoggerFactory.getLogger(TSAAirportRepository.class);
    private Map<String, TSAAirport> airportMap = new HashMap<>();

    public TSAAirportRepository(ObjectMapper objectMapper) throws IOException {
        InputStream airportCheckpointJsonStream = getClass().getClassLoader().getResourceAsStream("airport-checkpoints.json");
        AirportList airportList = objectMapper.readValue(airportCheckpointJsonStream, AirportList.class);
        airportList.getAirports().forEach(airportNode -> airportMap.put(airportNode.getAirport().getShortcode(), airportNode.getAirport()));
    }

    public Optional<TSAAirport> getAirport(String shortCode){
        return Optional.ofNullable(airportMap.get(shortCode));
    }

    public int totalAirportCount() {
        return airportMap.keySet().size();
    }

    public List<TSAAirport> findAirportsWithCheckpointCount(int count){
        return airportMap.values().stream().filter(tsaAirport -> tsaAirport.getCheckpoints().size() == count).collect(Collectors.toList());
    }
}
