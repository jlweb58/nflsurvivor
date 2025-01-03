package com.webber.nflsurvivor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "espn_id", unique = true)
    private Long espnId;

    @ManyToOne
    @JoinColumn (name = "home_team_id", referencedColumnName = "id", nullable = false)
    private Team homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", nullable = false)
    private Team awayTeam;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "home_points")
    private Integer homePoints;

    @Column(name = "away_points")
    private Integer awayPoints;

    @Column(name = "point_spread")
    private Double pointSpread;

    @Column(name = "finished")
    private boolean finished = false;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "id", nullable = false)
    private Stadium venue;

    protected Game() {}

    public Game(Team homeTeam, Team awayTeam, Integer week, Instant startTime) {
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

    public Instant getStartTime() {
        return startTime;
    }

    public Long getId() {
        return id;
    }

    public Long getEspnId() {
        return espnId;
    }

    public Game setEspnId(Long espnId) {
        this.espnId  = espnId;
        return this;
    }

    public Game setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getHomePoints() {
        return homePoints;
    }

    public Game setHomePoints(Integer homePoints) {
        this.homePoints = homePoints;
        return this;
    }

    public boolean isFinished() {
        return finished;
    }

    public Game setFinished(boolean finished) {
        this.finished = finished;
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

    public Stadium getVenue() {
        return venue;
    }

    public Game setVenue(Stadium venue) {
        this.venue = venue;
        return this;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", espnId=" + espnId +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", year=" + year +
                ", week=" + week +
                ", startTime=" + startTime +
                ", homePoints=" + homePoints +
                ", awayPoints=" + awayPoints +
                ", pointSpread=" + pointSpread +
                '}';
    }
}
