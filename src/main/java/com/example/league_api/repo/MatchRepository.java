package com.example.league_api.repo;

import com.example.league_api.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository
        extends JpaRepository<MatchEntity, Long> {

    Optional<MatchEntity> findByFixtureFixtureId(Long fixtureId);
}