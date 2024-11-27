package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
public class Game {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn (name = "home_team_id", referencedColumnName = "id", nullable = false)
    private Team homeTeam;

    @OneToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", nullable = false)
    private Team awayTeam;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "start_time", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime startTime;

    protected Game() {}

    public Game(Team homeTeam, Team awayTeam, Integer week, OffsetDateTime startTime) {}

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
