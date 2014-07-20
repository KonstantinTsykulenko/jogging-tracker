package com.tsykul.joggingtracker.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author KonstantinTsykulenko
 * @since 7/15/2014.
 */
@Entity
@Table(name = "jog_record")
public class JogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Long distance;

    @Column(nullable = false)
    private Long duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public JogRecord() {
    }

    public JogRecord(Date date, Long distance, Long duration) {
        this.date = date;
        this.distance = distance;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
