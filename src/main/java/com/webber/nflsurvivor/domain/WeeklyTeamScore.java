package com.webber.nflsurvivor.domain;

import java.util.Objects;

public class WeeklyTeamScore {

    private int winCount;

    private int lossCount;

    private int tieCount;

    public WeeklyTeamScore(int winCount, int lossCount, int tieCount) {
        this.winCount = winCount;
        this.lossCount = lossCount;
        this.tieCount = tieCount;
    }

    public WeeklyTeamScore() {

    }

    public WeeklyTeamScore incrementWinCount() {
        winCount++;
        return this;
    }

    public WeeklyTeamScore incrementLossCount() {
        lossCount++;
        return this;
    }

    public WeeklyTeamScore incrementTieCount() {
        tieCount++;
        return this;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLossCount() {
        return lossCount;
    }

    public int getTieCount() {
        return tieCount;
    }

    @Override
    public String toString() {
        return "WeeklyTeamScore{" +
                "winCount=" + winCount +
                ", lossCount=" + lossCount +
                ", tieCount=" + tieCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyTeamScore that = (WeeklyTeamScore) o;
        return winCount == that.winCount && lossCount == that.lossCount && tieCount == that.tieCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(winCount, lossCount, tieCount);
    }
}
