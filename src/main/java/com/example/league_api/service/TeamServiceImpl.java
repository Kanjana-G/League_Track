package com.example.league_api.service;

import com.example.league_api.entity.TeamEntity;
import com.example.league_api.repo.TeamRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.league_api.repo.FixtureRepository;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final StandingsService standingsService;
    private final FixtureRepository fixtureRepository;
    public TeamServiceImpl(
        TeamRepository teamRepository,
        StandingsService standingsService,
        FixtureRepository fixtureRepository) {

    this.teamRepository = teamRepository;
    this.standingsService = standingsService;
    this.fixtureRepository = fixtureRepository;
}

    @Override
    @Transactional
    public TeamEntity registerTeam(TeamEntity team) {

        if (teamRepository.existsByTeamName(team.getTeamName())) {
            throw new IllegalArgumentException(
                    "Team name already exists");
        }

        // Save team
        TeamEntity savedTeam = teamRepository.save(team);

        // Automatically create standings entry
        standingsService.createStanding(savedTeam.getTeamId());

        return savedTeam;
    }

    @Override
    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public TeamEntity getTeamById(Long id) {

        return teamRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Team not found with ID: " + id));
    }

    @Override
    public TeamEntity updateTeam(
            Long id,
            TeamEntity updatedTeam) {

        TeamEntity team = getTeamById(id);

        team.setTeamName(updatedTeam.getTeamName());

        return teamRepository.save(team);
    }

   @Override
@Transactional
public void deleteTeam(Long id) {

    if (!teamRepository.existsById(id)) {
        throw new IllegalArgumentException(
                "Team not found with ID: " + id);
    }

    // Check whether team is already part of a fixture
    if (fixtureRepository
            .existsByTeam1TeamIdOrTeam2TeamId(id, id)) {

        throw new IllegalArgumentException(
                "Cannot delete team because fixtures already exist for this team");
    }

    // Delete standings first
    standingsService.deleteStandingByTeam(id);

    // Delete team
    teamRepository.deleteById(id);
}
}