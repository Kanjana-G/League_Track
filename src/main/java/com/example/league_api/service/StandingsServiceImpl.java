package com.example.league_api.service;

import com.example.league_api.entity.StandingsEntryEntity;
import com.example.league_api.entity.TeamEntity;
import com.example.league_api.repo.StandingsEntryRepository;
import com.example.league_api.repo.TeamRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final StandingsEntryRepository standingsRepository;
    private final TeamRepository teamRepository;

    @Value("${league.points.win}")
    private int winPoints;

    @Value("${league.points.draw}")
    private int drawPoints;

    @Value("${league.points.loss}")
    private int lossPoints;

    public StandingsServiceImpl(
            StandingsEntryRepository standingsRepository,
            TeamRepository teamRepository) {

        this.standingsRepository = standingsRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public StandingsEntryEntity createStanding(Long teamId) {

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Team not found"));

        if (standingsRepository.findByTeamTeamId(teamId).isPresent()) {
            throw new IllegalArgumentException(
                    "Standings entry already exists for this team");
        }

        StandingsEntryEntity standing =
                new StandingsEntryEntity(team);

        return standingsRepository.save(standing);
    }

    @Override
    public List<StandingsEntryEntity> getStandings() {

        return standingsRepository.findAllByOrderByPointsDesc();
    }

    @Override
    public StandingsEntryEntity getStandingByTeam(Long teamId) {

        return standingsRepository.findByTeamTeamId(teamId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Standings entry not found for team"));
    }

    @Override
    public void recordWin(Long teamId) {

        StandingsEntryEntity standing =
                getStandingByTeam(teamId);

        standing.setPlayed(standing.getPlayed() + 1);
        standing.setWins(standing.getWins() + 1);
        standing.setPoints(
                standing.getPoints() + winPoints);

        standingsRepository.save(standing);
    }

    @Override
    public void recordDraw(Long teamId) {

        StandingsEntryEntity standing =
                getStandingByTeam(teamId);

        standing.setPlayed(standing.getPlayed() + 1);
        standing.setDraws(standing.getDraws() + 1);
        standing.setPoints(
                standing.getPoints() + drawPoints);

        standingsRepository.save(standing);
    }

    @Override
    public void recordLoss(Long teamId) {

        StandingsEntryEntity standing =
                getStandingByTeam(teamId);

        standing.setPlayed(standing.getPlayed() + 1);
        standing.setLosses(standing.getLosses() + 1);
        standing.setPoints(
                standing.getPoints() + lossPoints);

        standingsRepository.save(standing);
        }
    @Override
    public void deleteStandingByTeam(Long teamId) {

    StandingsEntryEntity standing =
            standingsRepository.findByTeamTeamId(teamId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Standings entry not found for team"));

    standingsRepository.delete(standing);
    }
    
}