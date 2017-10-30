package com.thoughtworks.mobile.poc.tsa.client.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Checkpoint{
    private int id;
    private String longname;
    private String shortname;
    boolean precheck;
    boolean precheckMilitary;
    List<PrecheckCarrier> precheckCarriers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getLongname() {
        return longname;
    }

    public String getShortname() {
        return shortname;
    }

    public boolean isPrecheck() {
        return precheck;
    }

    public boolean isPrecheckMilitary() {
        return precheckMilitary;
    }

    public List<String> getPrecheckCarriers() {
        return precheckCarriers.stream().map(PrecheckCarrier::getName).collect(Collectors.toList());
    }
}
