package com.dj.v_02.enrollment;

import com.dj.v_02.course.Course;
import com.dj.v_02.score.Score;
import com.dj.v_02.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "enrollments")
@Entity(name = "Enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "enrollment", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Score> scores;
    @Column(nullable = false)
    private int timesTaken;
    @Enumerated(EnumType.STRING)
    private EnrollmentStatusEnum status;
    @Enumerated(EnumType.STRING)
    private EnrollmentYearEnum year;
    @Enumerated(EnumType.STRING)
    private EnrollmentCycleEnum cycle;
    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{1,3}$")
    private String unit;
    @Enumerated(EnumType.STRING)
    private EnrollmentSemesterEnum semester;
    @Enumerated(EnumType.STRING)
    private EnrollmentSectionEnum section;
    @Column(nullable = true)
    private String note1;
    @Column(nullable = true)
    private String note2;
    @Column(nullable = true)
    private String note3;
    @Column(nullable = true)
    private String average;

    public Enrollment(EnrollmentRegisterDto enrollmentRegisterDto, Course course, User user, int timesTaken, EnrollmentStatusEnum status) {
        this.course = course;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.timesTaken = timesTaken;
        this.status = status;
        this.year = EnrollmentYearEnum.fromYear(enrollmentRegisterDto.year());
        this.cycle = EnrollmentCycleEnum.fromCycle(enrollmentRegisterDto.cycle());
        this.unit = enrollmentRegisterDto.unit();
        this.semester = EnrollmentSemesterEnum.valueOf(enrollmentRegisterDto.semester());
        this.section = EnrollmentSectionEnum.valueOf(enrollmentRegisterDto.section());
        this.note1 = null;
        this.note2 = null;
        this.note3 = null;
        this.average = null;
    }
}
