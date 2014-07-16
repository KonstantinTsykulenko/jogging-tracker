package com.tsykul.joggingtracker.model;

import java.util.Date;

/**
 * @author KonstantinTsykulenko
 * @since 7/16/2014.
 */
public class JogRecordModel {
    private Date date;
    private Long duration;
    private Long distance;

    public JogRecordModel(Date date, Long duration, Long distance) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
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
}
