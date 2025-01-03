package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    private transient WeeklyTeamScore weeklyTeamScore;

    protected Team() {

    }

    public Team(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setWeeklyTeamScore(WeeklyTeamScore weeklyTeamScore) {
        this.weeklyTeamScore = weeklyTeamScore;
    }

    public WeeklyTeamScore getWeeklyTeamScore() {
        return weeklyTeamScore;
    }

    @Override
    public String toString() {
        return name;
    }
}
