package com.tsykul.joggingtracker.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tsykul.joggingtracker.json.SpeedSerializer;

import java.math.BigDecimal;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JoggingReportModel {

    @JsonSerialize(using = SpeedSerializer.class)
    private Double averageDistance;
    @JsonSerialize(using = SpeedSerializer.class)
    private Double averageTime;
    private String weekOfYear;
    @JsonSerialize(using = SpeedSerializer.class)
    private Double averageSpeed;

    public JoggingReportModel(BigDecimal averageDistance, BigDecimal averageTime, BigDecimal averageSpeed, String weekOfYear) {
        this.averageDistance = averageDistance.doubleValue();
        this.averageTime = averageTime.doubleValue();
        this.averageSpeed = averageSpeed.doubleValue();
        this.weekOfYear = weekOfYear;
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

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(String weekOfYear) {
        this.weekOfYear = weekOfYear;
    }
}
