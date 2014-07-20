package com.tsykul.joggingtracker.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tsykul.joggingtracker.json.DateSerializer;
import com.tsykul.joggingtracker.json.SpeedSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JogRecordModel {
    private static final int METERS_IN_KM = 1000;
    private static final int SECONDS_IN_HR = 360;

    @JsonSerialize(using = DateSerializer.class)
    @NotNull
    private Date date;
    @NotNull
    @Min(1)
    private Long duration;
    @NotNull
    @Min(1)
    private Long distance;

    @JsonSerialize(using = SpeedSerializer.class)
    private Double speed;

    private Long id;

    public JogRecordModel() {
    }

    public JogRecordModel(Date date, Long duration, Long distance, Long id) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.id = id;
        this.speed = calculateSpeed(duration, distance);
    }

    private double calculateSpeed(Long duration, Long distance) {
        return (double) ((double) distance / METERS_IN_KM) / ((double) duration / SECONDS_IN_HR);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSpeed() {
        if (speed == null) {
            speed = calculateSpeed(duration, distance);
        }
        return speed;
    }
}
