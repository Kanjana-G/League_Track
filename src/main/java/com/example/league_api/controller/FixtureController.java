package com.example.league_api.controller;

import com.example.league_api.entity.FixtureEntity;
import com.example.league_api.service.FixtureService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixtures")
public class FixtureController {

    private final FixtureService fixtureService;

    public FixtureController(FixtureService fixtureService) {
        this.fixtureService = fixtureService;
    }

    // Generate round-robin fixtures
    @PostMapping("/generate")
    public ResponseEntity<List<FixtureEntity>> generateFixtures() {

        return new ResponseEntity<>(
                fixtureService.generateFixtures(),
                HttpStatus.CREATED
        );
    }

    // View all fixtures
    @GetMapping
    public ResponseEntity<List<FixtureEntity>> getAllFixtures() {

        return ResponseEntity.ok(
                fixtureService.getAllFixtures()
        );
    }

    // View one fixture
    @GetMapping("/{id}")
    public ResponseEntity<FixtureEntity> getFixtureById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                fixtureService.getFixtureById(id)
        );
    }
}