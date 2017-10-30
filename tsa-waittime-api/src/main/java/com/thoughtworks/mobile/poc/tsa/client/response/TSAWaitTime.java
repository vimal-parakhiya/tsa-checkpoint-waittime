package com.thoughtworks.mobile.poc.tsa.client.response;

import java.util.Date;

public class TSAWaitTime {
    private String CheckpointIndex;
    private String WaitTime;
    private String Created_Datetime;

    public TSAWaitTime() {
    }

    public TSAWaitTime(int waitTime) {
        WaitTime = Integer.toString(waitTime);
    }

    public int getCheckpointIndex() {
        return Integer.parseInt(CheckpointIndex);
    }

    public int getWaitTime() {
        return Integer.parseInt(WaitTime);
    }

    public int getCheckPointIndex() {
        return Integer.parseInt(CheckpointIndex);
    }

}
