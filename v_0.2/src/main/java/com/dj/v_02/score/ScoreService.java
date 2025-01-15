package com.dj.v_02.score;

import com.dj.v_02.enrollment.Enrollment;
import com.dj.v_02.enrollment.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final EnrollmentRepository enrollmentRepository;

    public ScoreService(ScoreRepository scoreRepository, EnrollmentRepository enrollmentRepository) {
        this.scoreRepository = scoreRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Score createScore(ScoreRegisterDto scoreRegisterDto) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(scoreRegisterDto.enrollmentId());
        if (enrollment.isEmpty()) {
            throw new EntityNotFoundException("Enrollment not found");
        }
        return scoreRepository.save(new Score(scoreRegisterDto, enrollment.get()));
    }
}
