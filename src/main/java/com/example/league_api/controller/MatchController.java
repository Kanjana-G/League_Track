package com.example.league_api.controller;

import com.example.league_api.entity.MatchEntity;
import com.example.league_api.service.MatchService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // Record result for a fixture
    @PostMapping("/fixture/{fixtureId}/result")
    public ResponseEntity<MatchEntity> recordResult(
            @PathVariable Long fixtureId,
            @Valid @RequestBody MatchEntity match) {

        return new ResponseEntity<>(
                matchService.recordResult(fixtureId, match),
                HttpStatus.CREATED
        );
    }

    // View all recorded matches
    @GetMapping
    public ResponseEntity<List<MatchEntity>> getAllMatches() {

        return ResponseEntity.ok(
                matchService.getAllMatches()
        );
    }

    // View one match
    @GetMapping("/{id}")
    public ResponseEntity<MatchEntity> getMatchById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                matchService.getMatchById(id)
        );
    }
}