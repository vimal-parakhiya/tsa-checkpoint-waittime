package com.thoughtworks.mobile.poc.tsa.web;

import com.thoughtworks.mobile.poc.tsa.domain.Airport;
import com.thoughtworks.mobile.poc.tsa.domain.SecurityCheckpoint;
import com.thoughtworks.mobile.poc.tsa.service.SecurityCheckpointWaitTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/airports")
public class AirportSecurityCheckpointController {
    private final static Logger logger = LoggerFactory.getLogger(AirportSecurityCheckpointController.class);

    private SecurityCheckpointWaitTimeService waitTimeService;

    @Autowired
    public AirportSecurityCheckpointController(SecurityCheckpointWaitTimeService waitTimeService) {
        this.waitTimeService = waitTimeService;
    }

    @RequestMapping("/{airportCode}")
    public ResponseEntity<Airport> getAllSecurityCheckpointWaitTime(@PathVariable("airportCode") String airportCode){
        logger.info("Processing request for airport code: {}", airportCode);
        Optional<Airport> airport = waitTimeService.fetchSecurityCheckpointWaitTimes(airportCode);
        return airport.isPresent() ?
                ResponseEntity.ok(airport.get()) :
                ResponseEntity.<Airport>notFound().build();
    }

    @RequestMapping("/{airportCode}/security-checkpoints/{checkpoint}")
    public ResponseEntity<List<SecurityCheckpoint>> getSecurityCheckpointWaitTime(@PathVariable("airportCode") String airportCode,
                                                                            @PathVariable("checkpoint") String checkPoint){
        logger.info("Processing request for airport code: {}, checkpoint name: {}", airportCode, checkPoint);
        return ResponseEntity.ok(fetchSecurityCheckpointWaitTimes(airportCode, checkPoint));
    }

    private List<SecurityCheckpoint> fetchSecurityCheckpointWaitTimes(String airportCode, String checkPointName) {
        Optional<Airport> airportOptional = waitTimeService.fetchSecurityCheckpointWaitTimes(airportCode);
        return airportOptional.isPresent() ?
                airportOptional.get().getSecurityCheckpoints(checkPointName) :
                new ArrayList<>();
    }
}
