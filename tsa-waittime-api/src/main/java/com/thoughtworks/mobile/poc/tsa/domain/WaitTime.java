package com.thoughtworks.mobile.poc.tsa.domain;

public class WaitTime {
    private int low;
    private int high;
    private Unit unit;

    private WaitTime() {
    }

    public WaitTime(int low, int high, Unit unit) {
        this.low = low;
        this.high = high;
        this.unit = unit;
    }

    public enum Unit {
        MINUTES
    }

    @Override
    public String toString() {
        return "WaitTime{" +
                "low=" + low +
                ", high=" + high +
                ", unit=" + unit +
                '}';
    }
}
