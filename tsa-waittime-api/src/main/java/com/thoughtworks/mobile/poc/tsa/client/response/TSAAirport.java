package com.thoughtworks.mobile.poc.tsa.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TSAAirport {
    private String name;
    private String shortcode;
    private String city;
    private String state;
    private double latitude;
    private double longitude;
    private int utc;
    private boolean dst;
    private boolean precheck;
    private boolean precheckMilitary;
    private List<Checkpoint> checkpoints = new ArrayList<>();

    @JsonCreator
    public TSAAirport(@JsonProperty("name") String name,
                      @JsonProperty("shortcode") String shortcode,
                      @JsonProperty("city") String city,
                      @JsonProperty("state") String state,
                      @JsonProperty("latitude") double latitude,
                      @JsonProperty("longitude") double longitude,
                      @JsonProperty("utc") int utc,
                      @JsonProperty("dst") boolean dst,
                      @JsonProperty("precheck") boolean precheck,
                      @JsonProperty("precheckMilitary") boolean precheckMilitary,
                      @JsonProperty("checkpoints") List<Checkpoint> checkpoints) {
        this.name = name;
        this.shortcode = shortcode;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.utc = utc;
        this.dst = dst;
        this.precheck = precheck;
        this.precheckMilitary = precheckMilitary;
        this.checkpoints.addAll(checkpoints);
        this.checkpoints.sort(Comparator.comparingInt(Checkpoint::getId));
    }

    public String getName() {
        return name;
    }

    public String getShortcode() {
        return shortcode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getUtc() {
        return utc;
    }

    public boolean isDst() {
        return dst;
    }

    public boolean isPrecheck() {
        return precheck;
    }

    public boolean isPrecheckMilitary() {
        return precheckMilitary;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
