package com.thoughtworks.mobile.poc.tsa.client.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TSAWaitTimeList {
    private List<TSAWaitTime> WaitTimes = new ArrayList<>();

    public List<TSAWaitTime> getWaitTimes(int index){
        return WaitTimes.stream().filter(tsaWaitTime -> tsaWaitTime.getCheckpointIndex() == index).collect(Collectors.toList());
    }
}
