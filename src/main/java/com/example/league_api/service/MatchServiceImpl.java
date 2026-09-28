package com.example.league_api.service;

import com.example.league_api.entity.FixtureEntity;
import com.example.league_api.entity.MatchEntity;

import com.example.league_api.repo.FixtureRepository;
import com.example.league_api.repo.MatchRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final FixtureRepository fixtureRepository;
    private final StandingsService standingsService;

    public MatchServiceImpl(
            MatchRepository matchRepository,
            FixtureRepository fixtureRepository,
            StandingsService standingsService) {

        this.matchRepository = matchRepository;
        this.fixtureRepository = fixtureRepository;
        this.standingsService = standingsService;
    }

    @Override
    @Transactional
    public MatchEntity recordResult(
            Long fixtureId,
            MatchEntity match) {

        FixtureEntity fixture = fixtureRepository
                .findById(fixtureId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Fixture not found with ID: " + fixtureId));

        // Prevent duplicate result
        if (matchRepository.findByFixtureFixtureId(fixtureId).isPresent()) {
            throw new IllegalArgumentException(
                    "Result has already been recorded for this fixture");
        }

        // Validate scores
        if (match.getTeam1Score() == null ||
                match.getTeam2Score() == null) {

            throw new IllegalArgumentException(
                    "Both team scores are required");
        }

        if (match.getTeam1Score() < 0 ||
                match.getTeam2Score() < 0) {

            throw new IllegalArgumentException(
                    "Scores cannot be negative");
        }

        match.setFixture(fixture);

        Long team1Id = fixture.getTeam1().getTeamId();
        Long team2Id = fixture.getTeam2().getTeamId();

        // Team 1 wins
        if (match.getTeam1Score() > match.getTeam2Score()) {

            match.setWinner(fixture.getTeam1());

            standingsService.recordWin(team1Id);
            standingsService.recordLoss(team2Id);
        }

        // Team 2 wins
        else if (match.getTeam2Score() > match.getTeam1Score()) {

            match.setWinner(fixture.getTeam2());

            standingsService.recordLoss(team1Id);
            standingsService.recordWin(team2Id);
        }

        // Draw
        else {

            match.setWinner(null);

            standingsService.recordDraw(team1Id);
            standingsService.recordDraw(team2Id);
        }

        match.setResultRecorded(true);

        return matchRepository.save(match);
    }

    @Override
    public List<MatchEntity> getAllMatches() {

        return matchRepository.findAll();
    }

    @Override
    public MatchEntity getMatchById(Long id) {

        return matchRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Match not found with ID: " + id));
    }
}