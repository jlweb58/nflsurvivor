package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "home_team_id", referencedColumnName = "id", nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", nullable = false)
    private Team awayTeam;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "start_time", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime startTime;

    @Column(name = "home_points")
    private Integer homePoints;

    @Column(name = "away_points")
    private Integer awayPoints;

    @Column(name = "point_spread")
    private Double pointSpread;

    protected Game() {}

    public Game(Team homeTeam, Team awayTeam, Integer week, OffsetDateTime startTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.week = week;
        this.startTime = startTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Integer getWeek() {
        return week;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public Long getId() {
        return id;
    }

    public Integer getHomePoints() {
        return homePoints;
    }

    public Game setHomePoints(Integer homePoints) {
        this.homePoints = homePoints;
        return this;
    }

    public Integer getAwayPoints() {
        return awayPoints;
    }

    public Game setAwayPoints(Integer awayPoints) {
        this.awayPoints = awayPoints;
        return this;
    }

    public Double getPointSpread() {
        return pointSpread;
    }

    public Game setPointSpread(Double pointSpread) {
        this.pointSpread = pointSpread;
        return this;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", week=" + week +
                ", startTime=" + startTime +
                '}';
    }
}
