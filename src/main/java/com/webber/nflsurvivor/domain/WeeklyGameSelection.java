package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

@Entity
public class WeeklyGameSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "week", nullable = false)
    private Integer week;

    @OneToOne
    @JoinColumn(name = "winning_team_id", referencedColumnName = "id", nullable = false)
    private Team winningTeam;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_result")
    private GameResult gameResult;

    protected WeeklyGameSelection() {

    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Integer getWeek() {
        return week;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    @Override
    public String toString() {
        return "WeeklyGameSelection{" +
                "id=" + id +
                ", user=" + user +
                ", week=" + week +
                ", winningTeam=" + winningTeam +
                ", gameResult=" + gameResult +
                '}';
    }
}
