package com.dj.v_02.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, String> {
}
