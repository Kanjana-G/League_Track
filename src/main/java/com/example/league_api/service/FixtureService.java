package com.example.league_api.service;

import com.example.league_api.entity.FixtureEntity;

import java.util.List;

public interface FixtureService {

    List<FixtureEntity> generateFixtures();

    List<FixtureEntity> getAllFixtures();

    FixtureEntity getFixtureById(Long id);
}