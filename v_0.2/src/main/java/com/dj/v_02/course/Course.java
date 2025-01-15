package com.dj.v_02.course;

import com.dj.v_02.asign.Asign;
import com.dj.v_02.career.Career;
import com.dj.v_02.enrollment.Enrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "courses")
@Entity(name = "Course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ]*$")
    private String name;
    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]$")
    private String credits;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String code;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SemesterEnum semester;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Asign> asigned;

    public Course(CourseRegisterDto courseRegisterDto, Career career) {
        this.name = courseRegisterDto.name().toUpperCase();
        this.credits = courseRegisterDto.credits();
        this.code = courseRegisterDto.code().toUpperCase();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.semester = SemesterEnum.valueOf(courseRegisterDto.semester().toUpperCase());
        this.career = career;
    }
}
