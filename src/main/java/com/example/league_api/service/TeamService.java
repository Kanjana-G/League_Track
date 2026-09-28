package com.example.league_api.service;

import com.example.league_api.entity.TeamEntity;
import java.util.List;

public interface TeamService {

    TeamEntity registerTeam(TeamEntity team);

    List<TeamEntity> getAllTeams();

    TeamEntity getTeamById(Long id);

    TeamEntity updateTeam(Long id, TeamEntity team);

    void deleteTeam(Long id);
}