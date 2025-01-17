package com.webber.nflsurvivor.season;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "season_weeks")
public class SeasonWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "season_year", nullable = false)
    private Integer seasonYear;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(name = "start_date", nullable = false)
    private Instant startDateTime;

    @Column(name = "end_date", nullable = false)
    private Instant endDateTime;

    protected SeasonWeek() {}

    public SeasonWeek(Integer seasonYear, Integer weekNumber, Instant startDateTime, Instant endDateTime) {
        this.seasonYear = seasonYear;
        this.weekNumber = weekNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Integer getSeasonYear() {
        return seasonYear;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toString() {
        return "SeasonWeek{" +
                "seasonYear=" + seasonYear +
                ", weekNumber=" + weekNumber +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
