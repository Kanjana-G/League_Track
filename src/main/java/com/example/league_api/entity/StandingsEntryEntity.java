package com.example.league_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "standings")
public class StandingsEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long standingId;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = false, unique = true)
    private TeamEntity team;

    private int played;
    private int wins;
    private int draws;
    private int losses;
    private int points;

    public StandingsEntryEntity() {
    }

    public StandingsEntryEntity(TeamEntity team) {
        this.team = team;
        this.played = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.points = 0;
    }

    public Long getStandingId() {
        return standingId;
    }

    public void setStandingId(Long standingId) {
        this.standingId = standingId;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}