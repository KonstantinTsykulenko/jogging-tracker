package com.tsykul.joggingtracker.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tsykul.joggingtracker.json.SpeedSerializer;

import java.math.BigDecimal;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JoggingReportModel {

    private BigDecimal averageDistance;
    private BigDecimal averageTime;
    private String weekOfYear;
    @JsonSerialize(using = SpeedSerializer.class)
    private Double averageSpeed;

    public JoggingReportModel(BigDecimal averageDistance, BigDecimal averageTime, BigDecimal averageSpeed, String weekOfYear) {
        this.averageDistance = averageDistance;
        this.averageTime = averageTime;
        this.averageSpeed = averageSpeed.doubleValue();
        this.weekOfYear = weekOfYear;
    }

    public BigDecimal getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(BigDecimal averageDistance) {
        this.averageDistance = averageDistance;
    }

    public BigDecimal getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(BigDecimal averageTime) {
        this.averageTime = averageTime;
    }

    public String getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(String weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }
}
