package com.webber.nflsurvivor.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "weekly_game_selections")
public class WeeklyGameSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "winning_team_id", referencedColumnName = "id", nullable = false)
    private Team winningTeamSelection;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_result")
    private GameResult gameResult;

    @ManyToOne
    @JoinColumn(name = "selected_game_id", referencedColumnName = "id", nullable = false)
    private Game selectedGame;

    protected WeeklyGameSelection() {

    }

    public WeeklyGameSelection(User user, Team winningTeamSelection, Game selectedGame) {
        this.user = user;
        this.winningTeamSelection = winningTeamSelection;
        this.selectedGame = selectedGame;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Team getWinningTeamSelection() {
        return winningTeamSelection;
    }

    public Game getSelectedGame() {
        return selectedGame;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public WeeklyGameSelection setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
        return this;
    }

    @Override
    public String toString() {
        return "WeeklyGameSelection{" +
                "id=" + id +
                ", user=" + user +
                ", game=" + selectedGame +
                ", winningTeam=" + winningTeamSelection +
                ", gameResult=" + gameResult +
                '}';
    }
}
