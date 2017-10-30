package com.thoughtworks.mobile.poc.tsa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.mobile.poc.tsa.client.TSAApiClient;
import com.thoughtworks.mobile.poc.tsa.domain.Airport;
import com.thoughtworks.mobile.poc.tsa.repository.TSAAirportRepository;
import com.thoughtworks.mobile.poc.tsa.service.SecurityCheckpointWaitTimeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.apache.coyote.http11.Constants.a;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TSAAirportRepository airportRepository;

    @Autowired
    private TSAApiClient apiClient;

    @Autowired
    private SecurityCheckpointWaitTimeService securityCheckpointWaitTimeService;

    @Test
    public void shouldDownloadCheckpointWaitTimes() {
        String waitTimeUrl = "https://apps.tsa.dhs.gov/MyTSAWebService/GetTSOWaitTimes.ashx?ap=ORD&output=json";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(waitTimeUrl, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());

        String checkpointUrl = "https://apps.tsa.dhs.gov/mytsawebservice/GetAirportCheckpoints.ashx?ap=ORD";
        responseEntity = restTemplate.getForEntity(checkpointUrl, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void shouldReturnCheckpointsForAllAirports() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://apps.tsa.dhs.gov/mytsawebservice/GetAirportCheckpoints.ashx?ap=all", String.class);
        System.out.println(response.getBody());
    }

    @Test
    public void shouldLoadSecurityWaitTimes() throws IOException {
        Optional<Airport> airport = securityCheckpointWaitTimeService.fetchSecurityCheckpointWaitTimes("JFK");
        System.out.println(airport.get());
    }

    @Test
    public void shouldPrintTotalAirportCount() {
        logger.info("Total Airports: {}", airportRepository.totalAirportCount());
    }

    @Test
    public void shouldFetchAirportsWithOneTerminal() {
        logger.info("Airport count with one terminal: {}", airportRepository.findAirportsWithCheckpointCount(1).size());

         logger.info("{}", Arrays.asList("string with space-and-hyphen".split("[ |-]")));
    }


}