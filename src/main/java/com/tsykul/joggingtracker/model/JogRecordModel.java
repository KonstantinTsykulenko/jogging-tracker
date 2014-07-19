package com.tsykul.joggingtracker.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tsykul.joggingtracker.json.CustomDateSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JogRecordModel {
    @JsonSerialize(using = CustomDateSerializer.class)
    @NotNull
    private Date date;
    @NotNull
    @Min(1)
    private Long duration;
    @NotNull
    @Min(1)
    private Long distance;

    private Long id;

    public JogRecordModel() {
    }

    public JogRecordModel(Date date, Long duration, Long distance, Long id) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.id = id;
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
}
