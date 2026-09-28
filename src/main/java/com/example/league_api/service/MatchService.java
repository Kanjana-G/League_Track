package com.example.league_api.service;

import com.example.league_api.entity.MatchEntity;

import java.util.List;

public interface MatchService {

    MatchEntity recordResult(Long fixtureId, MatchEntity match);

    List<MatchEntity> getAllMatches();

    MatchEntity getMatchById(Long id);
}