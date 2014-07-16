package com.tsykul.joggingtracker.model;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JoggingReportModel {
    private Double averageDistance;
    private Double averageTime;

    public JoggingReportModel(Double averageDistance, Double averageTime) {
        this.averageDistance = averageDistance;
        this.averageTime = averageTime;
    }

    public Double getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(Double averageDistance) {
        this.averageDistance = averageDistance;
    }

    public Double getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Double averageTime) {
        this.averageTime = averageTime;
    }
}
