package com.example.league_api.service;

import com.example.league_api.entity.StandingsEntryEntity;

import java.util.List;

public interface StandingsService {

    StandingsEntryEntity createStanding(Long teamId);

    List<StandingsEntryEntity> getStandings();

    StandingsEntryEntity getStandingByTeam(Long teamId);

    void recordWin(Long teamId);

    void recordDraw(Long teamId);

    void recordLoss(Long teamId);

    void deleteStandingByTeam(Long teamId);
}