package com.thoughtworks.mobile.poc.tsa.client.response;

import java.util.ArrayList;
import java.util.List;

public class AirportList {
    private List<AirportNode> airports = new ArrayList<>();

    public List<AirportNode> getAirports() {
        return airports;
    }
}
