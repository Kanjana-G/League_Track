package com.example.league_api.repo;

import com.example.league_api.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    boolean existsByTeamName(String teamName);
}