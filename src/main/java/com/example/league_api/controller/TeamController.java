package com.example.league_api.controller;

import com.example.league_api.entity.TeamEntity;
import com.example.league_api.service.TeamService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    // Constructor injection
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 1. Register a team
    @PostMapping
    public ResponseEntity<TeamEntity> registerTeam(
            @Valid @RequestBody TeamEntity team) {

        TeamEntity savedTeam = teamService.registerTeam(team);

        return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
    }

    // 2. Get all teams
    @GetMapping
    public ResponseEntity<List<TeamEntity>> getAllTeams() {

        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // 3. Get team by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamEntity> getTeamById(
            @PathVariable Long id) {

        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    // 4. Update team
    @PutMapping("/{id}")
    public ResponseEntity<TeamEntity> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamEntity team) {

        return ResponseEntity.ok(
                teamService.updateTeam(id, team)
        );
    }

    // 5. Delete team
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(
            @PathVariable Long id) {

        teamService.deleteTeam(id);

        return ResponseEntity.ok("Team deleted successfully");
    }
}