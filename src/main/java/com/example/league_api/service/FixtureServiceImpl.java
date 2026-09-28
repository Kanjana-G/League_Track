package com.example.league_api.service;

import com.example.league_api.entity.FixtureEntity;
import com.example.league_api.entity.TeamEntity;
import com.example.league_api.repo.FixtureRepository;
import com.example.league_api.repo.TeamRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FixtureServiceImpl implements FixtureService {

    private final FixtureRepository fixtureRepository;
    private final TeamRepository teamRepository;

    public FixtureServiceImpl(
            FixtureRepository fixtureRepository,
            TeamRepository teamRepository) {

        this.fixtureRepository = fixtureRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<FixtureEntity> generateFixtures() {

        List<TeamEntity> teams = teamRepository.findAll();

        if (teams.size() < 2) {
            throw new IllegalArgumentException(
                    "At least 2 teams are required to generate fixtures");
        }

        if (fixtureRepository.count() > 0) {
            throw new IllegalArgumentException(
                    "Fixtures have already been generated");
        }

        List<FixtureEntity> fixtures = new ArrayList<>();

        int round = 1;

        for (int i = 0; i < teams.size(); i++) {

            for (int j = i + 1; j < teams.size(); j++) {

                FixtureEntity fixture =
                        new FixtureEntity(
                                round,
                                teams.get(i),
                                teams.get(j)
                        );

                fixtures.add(fixture);

                round++;
            }
        }

        return fixtureRepository.saveAll(fixtures);
    }

    @Override
    public List<FixtureEntity> getAllFixtures() {
        return fixtureRepository.findAll();
    }

    @Override
    public FixtureEntity getFixtureById(Long id) {

        return fixtureRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Fixture not found with ID: " + id));
    }
}