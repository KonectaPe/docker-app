package com.dj.v_02.score;

import com.dj.v_02.enrollment.Enrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "scores")
@Entity(name = "Score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{1,2}$")
    private String score;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Score(ScoreRegisterDto scoreRegisterDto, Enrollment enrollment) {
        this.score = scoreRegisterDto.score();
        this.createdAt = LocalDateTime.now();
        this.enrollment = enrollment;
    }
}
