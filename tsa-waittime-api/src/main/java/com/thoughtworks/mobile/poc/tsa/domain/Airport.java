package com.thoughtworks.mobile.poc.tsa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Airport {
    private String code;
    private String name;
    private List<SecurityCheckpoint> securityCheckpoints = new ArrayList<>();

    private Airport() {
    }

    public Airport(String code, String name, List<SecurityCheckpoint> securityCheckpoints) {
        this.code = code;
        this.name = name;
        this.securityCheckpoints.addAll(securityCheckpoints);
    }

    public List<SecurityCheckpoint> getSecurityCheckpoints(String checkpointName){
        return securityCheckpoints.size() == 1 ?
                securityCheckpoints :
                securityCheckpoints
                .stream()
                .filter(securityCheckpoint -> securityCheckpoint.matches(checkpointName))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Airport{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", securityCheckpoints=" + securityCheckpoints +
                '}';
    }
}
