package com.tsykul.joggingtracker.model;

import java.math.BigDecimal;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JoggingReportModel {
    private BigDecimal averageDistance;
    private BigDecimal averageTime;
    private String weekOFYear;

    public JoggingReportModel(BigDecimal averageDistance, BigDecimal averageTime, String weekOFYear) {
        this.averageDistance = averageDistance;
        this.averageTime = averageTime;
        this.weekOFYear = weekOFYear;
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

    public String getWeekOFYear() {
        return weekOFYear;
    }

    public void setWeekOFYear(String weekOFYear) {
        this.weekOFYear = weekOFYear;
    }
}
