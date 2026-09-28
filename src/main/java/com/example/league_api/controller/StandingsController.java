package com.example.league_api.controller;

import com.example.league_api.entity.StandingsEntryEntity;
import com.example.league_api.service.StandingsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/standings")
public class StandingsController {

    private final StandingsService standingsService;

    public StandingsController(StandingsService standingsService) {
        this.standingsService = standingsService;
    }

    @PostMapping("/{teamId}")
    public ResponseEntity<StandingsEntryEntity> createStanding(
            @PathVariable Long teamId) {

        return new ResponseEntity<>(
                standingsService.createStanding(teamId),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<StandingsEntryEntity>> getStandings() {

        return ResponseEntity.ok(
                standingsService.getStandings()
        );
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<StandingsEntryEntity> getStandingByTeam(
            @PathVariable Long teamId) {

        return ResponseEntity.ok(
                standingsService.getStandingByTeam(teamId)
        );
    }
}