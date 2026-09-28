package com.example.league_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "matches")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @OneToOne
    @JoinColumn(name = "fixture_id", nullable = false, unique = true)
    private FixtureEntity fixture;

    @PositiveOrZero(message = "Team 1 score cannot be negative")
    private Integer team1Score;

    @PositiveOrZero(message = "Team 2 score cannot be negative")
    private Integer team2Score;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private TeamEntity winner;

    private boolean resultRecorded = false;

    public MatchEntity() {
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public FixtureEntity getFixture() {
        return fixture;
    }

    public void setFixture(FixtureEntity fixture) {
        this.fixture = fixture;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public TeamEntity getWinner() {
        return winner;
    }

    public void setWinner(TeamEntity winner) {
        this.winner = winner;
    }

    public boolean isResultRecorded() {
        return resultRecorded;
    }

    public void setResultRecorded(boolean resultRecorded) {
        this.resultRecorded = resultRecorded;
    }
}