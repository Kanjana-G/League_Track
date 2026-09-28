package com.example.league_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fixtures")
public class FixtureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fixtureId;

    private int roundNumber;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private TeamEntity team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private TeamEntity team2;

    public FixtureEntity() {
    }

    public FixtureEntity(int roundNumber, TeamEntity team1, TeamEntity team2) {
        this.roundNumber = roundNumber;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(Long fixtureId) {
        this.fixtureId = fixtureId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public TeamEntity getTeam1() {
        return team1;
    }

    public void setTeam1(TeamEntity team1) {
        this.team1 = team1;
    }

    public TeamEntity getTeam2() {
        return team2;
    }

    public void setTeam2(TeamEntity team2) {
        this.team2 = team2;
    }
}