package com.example.league_api.repo;

import com.example.league_api.entity.FixtureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureRepository
        extends JpaRepository<FixtureEntity, Long> {

    boolean existsByTeam1TeamIdOrTeam2TeamId(
            Long team1Id,
            Long team2Id
    );
}