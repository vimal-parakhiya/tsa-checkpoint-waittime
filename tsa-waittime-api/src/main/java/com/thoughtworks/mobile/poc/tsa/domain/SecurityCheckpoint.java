package com.thoughtworks.mobile.poc.tsa.domain;

import com.thoughtworks.mobile.poc.tsa.client.response.TSAWaitTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.apache.commons.collections4.IterableUtils.contains;

public class SecurityCheckpoint {
    private final static Logger logger = LoggerFactory.getLogger(SecurityCheckpoint.class);

    private String shortName;
    private String longName;
    private boolean hasPrecheck;
    private List<WaitTime> waitTimes = new ArrayList<WaitTime>();
    private List<String> airlines = new ArrayList<>();
    private WaitTime averageWaitTime;
    private WaitTime minWaitTime;
    private WaitTime maxWaitTime;

    public SecurityCheckpoint(String shortName, String longName,
                              boolean hasPrecheck,
                              List<TSAWaitTime> tsaWaitTimes,
                              List<String> airlines) {
        this.shortName = shortName;
        this.longName = longName;
        this.hasPrecheck = hasPrecheck;
        this.waitTimes.addAll(buildWaitTimes(tsaWaitTimes));
        this.airlines.addAll(airlines);
        averageWaitTime = computeAverageWaitTime(tsaWaitTimes);
        minWaitTime = findMinWaitTime(tsaWaitTimes);
        maxWaitTime = findMaxWaitTime(tsaWaitTimes);
    }

    private WaitTime findMinWaitTime(List<TSAWaitTime> tsaWaitTimes) {
        if (!tsaWaitTimes.isEmpty()) {
            TSAWaitTime minTSAWaitTime = Collections.min(tsaWaitTimes, Comparator.comparingInt(TSAWaitTime::getWaitTime));
            return buildWaitTime(minTSAWaitTime);
        }
        return null;
    }

    private WaitTime findMaxWaitTime(List<TSAWaitTime> tsaWaitTimes) {
        if (!tsaWaitTimes.isEmpty()) {
            TSAWaitTime maxTSAWaitTime = Collections.max(tsaWaitTimes, Comparator.comparingInt(TSAWaitTime::getWaitTime));
            return buildWaitTime(maxTSAWaitTime);
        }
        return null;
    }

    private WaitTime computeAverageWaitTime(List<TSAWaitTime> tsaWaitTimes) {
        if (!tsaWaitTimes.isEmpty()) {
            TSAWaitTime averageTSAWaitTime = tsaWaitTimes
                    .stream()
                    .reduce(new TSAWaitTime(0), (accumulator, element) -> new TSAWaitTime(accumulator.getWaitTime() + element.getWaitTime()));
            return buildWaitTime(new TSAWaitTime(averageTSAWaitTime.getWaitTime() / tsaWaitTimes.size()));
        }
        return null;
    }

    private List<WaitTime> buildWaitTimes(List<TSAWaitTime> tsaWaitTimes) {
        return tsaWaitTimes.stream().map(this::buildWaitTime).collect(Collectors.toList());
    }

    private WaitTime buildWaitTime(TSAWaitTime tsaWaitTime) {
        int lowestWaitTime = (tsaWaitTime.getWaitTime() - 1) * 10 + 1;
        int highestWaitTime = tsaWaitTime.getWaitTime() * 10;
        return new WaitTime(lowestWaitTime, highestWaitTime, WaitTime.Unit.MINUTES);
    }

    public String getLongName() {
        return longName;
    }

    public boolean matches(String name) {
        return name == null
                || isFullNameMatches(name, longName)
                || isFullNameMatches(name, shortName)
                || isPartialNameMatches(name, longName)
                || isPartialNameMatches(name, shortName);
    }

    private boolean isPartialNameMatches(String inputTerminalName, String terminalName) {
        Set<String> inputTerminalNameElements = terminalNameElements(inputTerminalName);
        Set<String> terminalNameElements = terminalNameElements(terminalName);

        return !terminalNameElements.isEmpty()
                && containsAll(terminalNameElements, inputTerminalNameElements);

    }

    private boolean containsAll(Collection<String> superSet, Collection<String> subset) {
        return subset
                .stream()
                .filter(element -> contains(superSet, element))
                .collect(Collectors.toSet())
                .size() == subset.size();
    }

    private boolean contains(Collection<String> elements, String input) {
        return elements.stream().anyMatch(element -> element.toLowerCase().contains(input.toLowerCase()));
    }

    private boolean isFullNameMatches(String input, String terminalName) {
        return input != null && input.equalsIgnoreCase(terminalName);
    }

    private Set<String> terminalNameElements(String name) {
//        logger.info("Before normalization: {}", name);
        name = name != null ? name : "";
        name = normalize(name);
//        logger.info("After normalization: {}", name);

        String[] elements = name.split(" ");
        return stream(elements)
                .map(element -> element == null ? "" : element.trim())
                .filter(element -> !element.isEmpty()
                        && !element.equalsIgnoreCase("Terminal")
                        && !element.equalsIgnoreCase("Term")
                        && !element.equalsIgnoreCase("Checkpoint"))
                .collect(Collectors.toSet());
    }

    private String normalize(String name) {
        return name
                .replaceAll("-", " ")
                .replaceAll("\\.", " ")
                .replaceAll(",", " ");
    }

    @Override
    public String toString() {
        return "SecurityCheckpoint{" +
                "shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", hasPrecheck=" + hasPrecheck +
                ", waitTimes=" + waitTimes +
                ", airlines=" + airlines +
                ", averageWaitTime=" + averageWaitTime +
                ", minWaitTime=" + minWaitTime +
                ", maxWaitTime=" + maxWaitTime +
                '}';
    }
}
