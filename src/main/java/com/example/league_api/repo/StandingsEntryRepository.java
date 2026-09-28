package com.example.league_api.repo;

import com.example.league_api.entity.StandingsEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StandingsEntryRepository
        extends JpaRepository<StandingsEntryEntity, Long> {

    Optional<StandingsEntryEntity> findByTeamTeamId(Long teamId);

    List<StandingsEntryEntity> findAllByOrderByPointsDesc();
}